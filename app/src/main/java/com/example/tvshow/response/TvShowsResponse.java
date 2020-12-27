package com.example.tvshow.response;

import com.example.tvshow.models.TVShow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowsResponse{
    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int totalPages;

    @SerializedName("tv_shows")
    private List<TVShow> tvShows;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<TVShow> getTvShows() {
        return tvShows;
    }
}

