package com.example.moviestar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewReleaseDate;
    private TextView textViewRating;
    private TextView textViewOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewRating = findViewById(R.id.textViewRating);
        textViewOverview = findViewById(R.id.textViewOverview);

        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String posterPath = intent.getStringExtra("poster_path");
            String releaseDate = intent.getStringExtra("release_date");
            double rating = intent.getDoubleExtra("rating", 0);
            String overview = intent.getStringExtra("overview");

            textViewTitle.setText(title);
            textViewReleaseDate.setText(releaseDate);
            textViewRating.setText(String.valueOf(rating));
            textViewOverview.setText(overview);

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500" + posterPath)
                    .into(imageViewPoster);
        }
    }
}
