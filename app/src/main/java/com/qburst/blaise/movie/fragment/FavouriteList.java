package com.qburst.blaise.movie.fragment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.qburst.blaise.movie.R;
import com.qburst.blaise.movie.database.DatabaseHelper;
import com.qburst.blaise.movie.models.Movie;
import com.qburst.blaise.movie.network.ApiClient;
import com.qburst.blaise.movie.network.ApiInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.qburst.blaise.movie.activity.MainActivity.pref;
import static com.qburst.blaise.movie.fragment.SlidePageFragment.API_KEY;

public class FavouriteList {
    private List<Movie> movies;
    private List<String> ids;
    private Set<String> favourite = pref.getStringSet("favSet",new HashSet<String>());
    private int i;
    private Context context;
    private View view;

    private Iterator iterator;
    List<Movie> getFavouriteMovies(View view, Context context) {
        this.movies = new ArrayList<>();
        this.ids = new ArrayList<>();
        this.context = context;
        this.view = view;
        DatabaseHelper db = new DatabaseHelper(context);
        this.ids = db.getFavoriteMovies();
        this.iterator = favourite.iterator();
        //getMovies();
        getMovies2();
        return movies;
    }

    private void getMovies2() {
        for(int j=0; j<ids.size(); j++) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Movie> call = apiService.getMovieDetails(ids.get(j), API_KEY);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    Movie movie = response.body();
                    movies.add(movie);
                    i++;
                    if (i == ids.size()) {
                        RecyclerView recyclerView = view.findViewById(R.id.recycler);
                        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                        CustomAdapter adapter = new CustomAdapter(context, movies,2);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                }
            });
        }
    }

    private void getMovies() {
        if(iterator.hasNext()) {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<Movie> call = apiService.getMovieDetails((String) iterator.next(), API_KEY);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    Movie movie = response.body();
                    movies.add(movie);
                    i++;
                    if (i == favourite.size()) {
                        RecyclerView recyclerView = view.findViewById(R.id.recycler);
                        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                        CustomAdapter adapter = new CustomAdapter(context, movies,2);
                        recyclerView.setAdapter(adapter);

                    } else {
                        getMovies();
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                }
            });
        }
    }
}
