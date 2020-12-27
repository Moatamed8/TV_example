package com.example.tvshow.viewmodels;

import com.example.tvshow.repositories.MostPopularTvShowsRepository;
import com.example.tvshow.response.TvShowsResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MostPopularTvShowsViewModel extends ViewModel {
    private MostPopularTvShowsRepository mostPopularTvShowsRepository;

    public MostPopularTvShowsViewModel() {
        mostPopularTvShowsRepository=new MostPopularTvShowsRepository();

    }
    public LiveData<TvShowsResponse> getMostPopularTVShows(int page){
        return mostPopularTvShowsRepository.getMostPopularTVShows(page);
    }
}

