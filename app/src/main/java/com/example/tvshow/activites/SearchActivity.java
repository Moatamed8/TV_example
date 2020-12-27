package com.example.tvshow.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;


import com.example.tvshow.R;
import com.example.tvshow.adapters.TVShowsAdapter;
import com.example.tvshow.databinding.ActivitySearchBinding;
import com.example.tvshow.listeners.TVShowListener;
import com.example.tvshow.models.TVShow;
import com.example.tvshow.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TVShowListener {
    private ActivitySearchBinding activitySearchBinding;
    private SearchViewModel viewModel;
    private List<TVShow> tvShows =new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage=1;
    private int totalAvailablePages =1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySearchBinding= DataBindingUtil.setContentView(this,R.layout.activity_search);
        getWindow().setStatusBarColor(ContextCompat.getColor(SearchActivity.this,R.color.colorPrimaryDark));
        doInitialization();
    }
    public void doInitialization(){
        activitySearchBinding.imageBack.setOnClickListener(v -> onBackPressed());
        activitySearchBinding.tvShowRecycleView.setHasFixedSize(true);
        viewModel=new ViewModelProvider(this).get(SearchViewModel.class);
        tvShowsAdapter=new TVShowsAdapter(tvShows,this);
        activitySearchBinding.tvShowRecycleView.setAdapter(tvShowsAdapter);
        activitySearchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer !=null){
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().trim().isEmpty()){
                    timer=new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                currentPage=1;
                                totalAvailablePages=1;
                                searchTVShow(s.toString());
                            });
                        }
                    },800);
                }else {
                    tvShows.clear();
                    tvShowsAdapter.notifyDataSetChanged();
                }
            }
        });
        activitySearchBinding.tvShowRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activitySearchBinding.tvShowRecycleView.canScrollVertically(1)){
                    if (!activitySearchBinding.inputSearch.getText().toString().isEmpty()){
                        if (currentPage < totalAvailablePages){
                            currentPage += 1;
                            searchTVShow(activitySearchBinding.inputSearch.getText().toString());
                        }

                    }
                }
            }
        });
        activitySearchBinding.inputSearch.requestFocus();
    }


    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent home = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        home.putExtra("tvShow", tvShow);
        startActivity(home);
    }
    public void searchTVShow(String query){
        toggleLoading();
        viewModel.searchTVShow(query,currentPage).observe(this,tvShowsResponse -> {
            toggleLoading();
            if (tvShowsResponse != null){
                totalAvailablePages=tvShowsResponse.getTotalPages();
                if (tvShowsResponse.getTvShows() !=null){
                    int oldCount=tvShows.size();
                    tvShows.addAll(tvShowsResponse.getTvShows());
                    tvShowsAdapter.notifyItemRangeInserted(oldCount,tvShows.size());
                }
            }
        });
    }
    public void toggleLoading() {
        if (currentPage == 1) {
            if (activitySearchBinding.getIsLoading() != null && activitySearchBinding.getIsLoading()) {
                activitySearchBinding.setIsLoading(false);
            } else {
                activitySearchBinding.setIsLoading(true);
            }
        } else {
            if (activitySearchBinding.getIsLoadingMore() != null && activitySearchBinding.getIsLoadingMore()) {
                activitySearchBinding.setIsLoadingMore(false);
            } else {
                activitySearchBinding.setIsLoadingMore(true);
            }
        }
    }
}