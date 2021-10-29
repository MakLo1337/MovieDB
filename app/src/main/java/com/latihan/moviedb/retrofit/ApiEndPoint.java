package com.latihan.moviedb.retrofit;

import com.latihan.moviedb.model.Credits;
import com.latihan.moviedb.model.Movies;
import com.latihan.moviedb.model.NowPlaying;
import com.latihan.moviedb.model.UpComing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {

    @GET("movie/{movie_id}")
    Call<Movies> getMovieById(
            @Path("movie_id") String movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/now_playing/")
    Call<NowPlaying> getNowPlaying(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("movie/upcoming/")
    Call<UpComing> getUpComing(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("movie/{movie_id}/credits")
    Call<Credits> getCredits(
            @Path("movie_id") String movieId,
            @Query("api_key") String apiKey
    );
}
