package com.qburst.blaise.movie.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qburst.blaise.movie.R;
import com.qburst.blaise.movie.activity.MovieViewActivity;
import com.qburst.blaise.movie.models.Movie;

import java.util.List;

import static android.support.v7.content.res.AppCompatResources.getDrawable;

class CustomAdapter extends RecyclerView.Adapter <CustomAdapter.MovieHolder>{

    public static final int TYPE_FOOTER = 2;
    public static final int TYPE_ITEM = 1;
    private Context context;
    private List<Movie> movies;
    private int pageIndex;

    CustomAdapter(Context context, List<Movie> movies, int i) {
        this.context = context;
        this.movies = movies;
        this.pageIndex = i;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        if(i == TYPE_ITEM){
            view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.single_item,viewGroup, false);
        }
        else {
            view = LayoutInflater.from(viewGroup.getContext()).
                    inflate(R.layout.button,viewGroup, false);
        }
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        final int j = i;
        if(i<movies.size()) {
            movieHolder.textView.setText(movies.get(i).getTitle());
            Glide.with(context).load("http://image.tmdb.org/t/p/w185" + movies.get(i).
                    getPosterPath()).apply(new RequestOptions().placeholder(getDrawable(context,
                    android.R.drawable.ic_menu_gallery))).into(movieHolder.imageView);

            movieHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MovieViewActivity.class);
                    intent.putExtra("id", String.valueOf(movies.get(j).getId()));
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (pageIndex == 2) {
            return TYPE_ITEM;
        }
        else {
            return (position == movies.size()) ? TYPE_FOOTER : TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (pageIndex == 2) {
            return movies.size();
        }
        else {
            return movies.size() + 1;
        }
    }


    class MovieHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private CardView cardView;
        MovieHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            cardView = itemView.findViewById(R.id.card);
        }
    }
}
