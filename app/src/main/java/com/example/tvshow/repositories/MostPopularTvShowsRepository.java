package com.example.tvshow.repositories;

import com.example.tvshow.network.ApiClient;
import com.example.tvshow.network.ApiService;
import com.example.tvshow.response.TvShowsResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTvShowsRepository {

    private ApiService apiService;
    public MostPopularTvShowsRepository() {
        apiService= ApiClient.getRetrofit().create(ApiService.class);


    }

    public LiveData<TvShowsResponse> getMostPopularTVShows(int page){
        MutableLiveData<TvShowsResponse> data=new MutableLiveData<>();
        apiService.getMostPopularTVShows(page).enqueue(new Callback<TvShowsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvShowsResponse> call, @NonNull Response<TvShowsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TvShowsResponse> call,@NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}

