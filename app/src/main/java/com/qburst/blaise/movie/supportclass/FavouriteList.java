package com.qburst.blaise.movie.supportclass;

import android.util.Log;

import com.qburst.blaise.movie.models.Movie;
import com.qburst.blaise.movie.network.ApiClient;
import com.qburst.blaise.movie.network.ApiInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.qburst.blaise.movie.activity.MainActivity.pref;
import static com.qburst.blaise.movie.fragment.SlidePageFragment.API_KEY;

public class FavouriteList {
    private List<Movie> movies;
    private Set<String> favourite = pref.getStringSet("favSet",new HashSet<String>());
    private int i;
    public List<Movie> getFavouriteMovies() {
        movies = new ArrayList<>();
        for(String s: favourite) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Movie> call = apiService.getMovieDetails(s, API_KEY);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    Movie movie = response.body();
                    movies.add(movie);
                    i++;
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                }
            });
        }
        if(i==favourite.size()) {

        }
        return movies;
    }
}
