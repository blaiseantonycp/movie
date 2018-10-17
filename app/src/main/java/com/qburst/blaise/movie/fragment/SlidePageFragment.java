package com.qburst.blaise.movie.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qburst.blaise.movie.R;
import com.qburst.blaise.movie.models.Movie;
import com.qburst.blaise.movie.models.MovieResponse;
import com.qburst.blaise.movie.network.ApiClient;
import com.qburst.blaise.movie.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.qburst.blaise.movie.activity.MainActivity.popularMovies;
import static com.qburst.blaise.movie.activity.MainActivity.popularPage;
import static com.qburst.blaise.movie.activity.MainActivity.topRatedMovies;
import static com.qburst.blaise.movie.activity.MainActivity.topRatedPage;

public class SlidePageFragment extends Fragment {

    public static String API_KEY = "ff85648a7658698ee49eca272f7076a3";
    private View v;

    public static Fragment newInstance(int i) {
        SlidePageFragment sf = new SlidePageFragment();
        Bundle bundle =new Bundle();
        bundle.putInt("position",i);
        sf.setArguments(bundle);
        return sf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie,container,false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

        this.v = view;
        assert savedInstanceState != null;
        Bundle bundle = getArguments();
        final int i = bundle.getInt("position");
        if (i == 2) {
            new FavouriteList().getFavouriteMovies(view, getContext());
        }
        else {
            setFragment(view,i,0);
        }
    }

    private void setFragment(final View view, final int i, final int position) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = null;
        switch (i) {
            case 0:
                call = apiService.getTopRatedMovies(API_KEY,topRatedPage);
                break;
            case 1:
                call = apiService.getPopularMovies(API_KEY,popularPage);
                break;
        }
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call,
                                   Response<MovieResponse> response) {
                List<Movie> movie = response.body().getResults();
                final List<Movie> movies = new ArrayList<>();
                if(i == 0) {
                    topRatedPage++;
                    topRatedMovies.addAll(movie);
                    movies.addAll(topRatedMovies);
                }
                else if(i == 1) {
                    popularPage++;
                    popularMovies.addAll(movie);
                    movies.addAll(popularMovies);
                }
                RecyclerView recyclerView = view.findViewById(R.id.recycler);
                CustomAdapter adapter = new CustomAdapter(getContext(),movies,i);
                adapter.setOnBottomReachedListener(new OnBottomReachedListener() {
                    @Override
                    public void onBottomReached(int k) {
                        if (k != 2) {
                            setFragment(view, i,movies.size()-3);
                        }
                    }
                });
                GridLayoutManager layout = new GridLayoutManager(getContext(),2);
                layout.scrollToPosition(position);
                recyclerView.setLayoutManager(layout);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("SlidePageFragment", "error");
            }
        });
    }
}
