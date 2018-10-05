package com.qburst.blaise.movie.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qburst.blaise.movie.R;
import com.qburst.blaise.movie.models.Movie;
import com.qburst.blaise.movie.network.ApiClient;
import com.qburst.blaise.movie.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewActivity extends Activity {

    private String API_KEY = "ff85648a7658698ee49eca272f7076a3";
    private int id= 0;
    private SharedPreferences pref;
    private ImageButton imageButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        imageButton = findViewById(R.id.imageButton2);
        pref = this.getSharedPreferences("Fav",MODE_PRIVATE);

        id =Integer.parseInt(getIntent().getStringExtra("id"));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call = apiService.getMovieDetails(id,API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                ImageView imageView =findViewById(R.id.imageView2);
                Glide.with(MovieViewActivity.this).
                        load("http://image.tmdb.org/t/p/w300"+movie.getBackdropPath())
                        .apply(new RequestOptions()).into(imageView);
                boolean fav = pref.getBoolean(String.valueOf(id), false);
                if(fav) {
                    imageButton.setImageDrawable(getResources().
                            getDrawable(android.R.drawable.star_big_on));
                }
                else {
                    imageButton.setImageDrawable(getResources().
                            getDrawable(android.R.drawable.star_big_off));
                }
                TextView title = findViewById(R.id.title);
                title.setText(movie.getOriginalTitle()+" (" +
                        Double.toString(movie.getVoteAverage())+")");
                TextView description = findViewById(R.id.description);
                description.setText(movie.getOverview());
                TextView releaseDate = findViewById(R.id.releasedate);
                releaseDate.setText("Release Date: "+movie.getReleaseDate());
                TextView titleEng = findViewById(R.id.titleEng);
                titleEng.setText(movie.getTitle());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void toggleFavButton(View view) {

        if(id != 0) {
            boolean fav = pref.getBoolean(String.valueOf(id), false);
            if(fav) {
                imageButton.setImageDrawable(getResources().
                        getDrawable(android.R.drawable.star_big_off));
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean(String.valueOf(id),false).apply();
            }
            else {
                imageButton.setImageDrawable(getResources().
                        getDrawable(android.R.drawable.star_big_on));
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean(String.valueOf(id),true).apply();
            }
        }
    }

    public void back(View view) {
        onBackPressed();
    }
}
