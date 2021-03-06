package com.qburst.blaise.movie.network;

import com.qburst.blaise.movie.models.Movie;
import com.qburst.blaise.movie.models.MovieResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") String id, @Query("api_key") String apiKey);
}