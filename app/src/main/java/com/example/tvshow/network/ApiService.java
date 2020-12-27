package com.example.tvshow.network;

import com.example.tvshow.response.TVShowDetailsResponse;
import com.example.tvshow.response.TvShowsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService{
    @GET("most-popular")
    Call<TvShowsResponse> getMostPopularTVShows(@Query("page") int page);

    @GET("show-details")
    Call<TVShowDetailsResponse> getTVShowDetails(@Query("q") String tvShowId);

    @GET("search")
    Call<TvShowsResponse> searchTVShow(@Query("q")String query,@Query("page") int page);


}
