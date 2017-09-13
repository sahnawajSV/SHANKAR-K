package com.android.movieapp.network;

import com.android.movieapp.modelobject.MovieDetailBaseResponse;
import com.android.movieapp.modelobject.MovieListBaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sahnawajbiswas on 9/12/17.
 */

public interface APIInterface {
    @GET("discover/movie?")
    Call<MovieListBaseResponse> getMovieList(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieDetailBaseResponse> getMovieDetails(@Path("movie_id") int id, @Query("api_key") String apiKey);


}


