package com.qburst.blaise.movie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.qburst.blaise.movie.R;
import com.qburst.blaise.movie.models.Movie;
import com.qburst.blaise.movie.models.MovieResponse;
import com.qburst.blaise.movie.network.ApiClient;
import com.qburst.blaise.movie.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewActivity extends AppCompatActivity {

    private String API_KEY = "ff85648a7658698ee49eca272f7076a3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        int id =Integer.parseInt(getIntent().getStringExtra("id"));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getMovieDetails(id,API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

}
