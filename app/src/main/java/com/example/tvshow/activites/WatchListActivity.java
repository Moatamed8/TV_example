package com.example.tvshow.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.tvshow.R;
import com.example.tvshow.adapters.WatchlistAdapter;
import com.example.tvshow.databinding.ActivityWatchListBinding;
import com.example.tvshow.listeners.WatchListListener;
import com.example.tvshow.models.TVShow;
import com.example.tvshow.utilities.TempDataHolder;
import com.example.tvshow.viewmodels.WatclistViewModel;

import java.util.ArrayList;
import java.util.List;

public class WatchListActivity extends AppCompatActivity implements WatchListListener {
    private ActivityWatchListBinding activityWatchListBinding;
    private WatclistViewModel viewModel;
    private WatchlistAdapter watchlistAdapter;
    private List<TVShow>watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding= DataBindingUtil.setContentView(this,R.layout.activity_watch_list);
        getWindow().setStatusBarColor(ContextCompat.getColor(WatchListActivity.this,R.color.colorPrimaryDark));
        doInitialization();
    }
    public void doInitialization(){
        viewModel=new ViewModelProvider(this).get(WatclistViewModel.class);
        activityWatchListBinding.imageBack.setOnClickListener(v -> onBackPressed());

        watchlist=new ArrayList<>();
        loadWatchList();

    }
    private void loadWatchList(){
        activityWatchListBinding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.loadWatchList().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShows -> {
                    activityWatchListBinding.setIsLoading(false);
                    if (watchlist.size() > 0){
                        watchlist.clear();
                    }
                    watchlist.addAll(tvShows);
                    watchlistAdapter=new WatchlistAdapter(watchlist,this);
                    activityWatchListBinding.watchlistRecycleView.setAdapter(watchlistAdapter);
                    activityWatchListBinding.watchlistRecycleView.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();
                }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TempDataHolder.IS_WATCHLIST_UPDATE){
            loadWatchList();
            TempDataHolder.IS_WATCHLIST_UPDATE=false;
        }
        loadWatchList();
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent =new Intent(getApplicationContext(),TVShowDetailsActivity.class);
        intent.putExtra("tvShow",tvShow);
        startActivity(intent);
    }

    @Override
    public void removeTVShowFromWatchList(TVShow tvShow, int position) {
        CompositeDisposable compositeDisposableForDelete =new CompositeDisposable();
        compositeDisposableForDelete.add(viewModel.removeFromWatchList(tvShow)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> {
            watchlist.remove(position);
            watchlistAdapter.notifyItemRemoved(position);
            watchlistAdapter.notifyItemRangeChanged(position,watchlistAdapter.getItemCount());
            compositeDisposableForDelete.dispose();
        }));

    }
}