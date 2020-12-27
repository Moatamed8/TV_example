package com.example.tvshow.listeners;

import com.example.tvshow.models.TVShow;

public interface WatchListListener {

    void onTVShowClicked(TVShow tvShow);

    void removeTVShowFromWatchList(TVShow tvShow, int position);
}
