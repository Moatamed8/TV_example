package com.example.tvshow.viewmodels;

import android.app.Application;

import com.example.tvshow.database.TVShowsDatabase;
import com.example.tvshow.models.TVShow;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatclistViewModel extends AndroidViewModel {
    private TVShowsDatabase tvShowsDatabase;
    public WatclistViewModel(@NonNull Application application){
        super(application);
        tvShowsDatabase=TVShowsDatabase.getTvShowsDatabase(application);


    }
    public Flowable<List<TVShow>> loadWatchList(){
        return tvShowsDatabase.tvShowData().getWatchList();
    }
    public Completable removeFromWatchList (TVShow tvShow){
        return tvShowsDatabase.tvShowData().removeFromWatchList(tvShow);
    }
}
