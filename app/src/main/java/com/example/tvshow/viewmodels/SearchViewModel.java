package com.example.tvshow.viewmodels;

import com.example.tvshow.repositories.SearchTVShowRepository;
import com.example.tvshow.response.TvShowsResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    private SearchTVShowRepository searchTVShowRepository;
    public SearchViewModel(){
        searchTVShowRepository=new SearchTVShowRepository();

    }
    public LiveData<TvShowsResponse> searchTVShow(String query ,int page){
        return searchTVShowRepository.searchTVShow(query,page);
    }
}
