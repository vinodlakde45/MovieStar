package com.example.moviestar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
        this.context = context;
        this.movies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        }

        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
        TextView textViewReleaseDate = convertView.findViewById(R.id.textViewReleaseDate);
        TextView textViewRating = convertView.findViewById(R.id.textViewRating);
        ImageView imageViewPoster = convertView.findViewById(R.id.imageViewPoster);

        textViewTitle.setText(movie.getTitle());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewRating.setText(String.valueOf(movie.getRating()));

        // Load movie poster using Glide
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                .into(imageViewPoster);

        return convertView;
    }
}
