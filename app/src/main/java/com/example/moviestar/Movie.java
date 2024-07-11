package com.example.moviestar;

public class Movie {
    private String title;
    private String posterPath;
    private String releaseDate;
    private double rating;
    private String overview;

    public Movie(String title, String posterPath, String releaseDate, double rating, String overview) {
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.rating = rating;
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getRating() {
        return rating;
    }

    public String getOverview() {
        return overview;
    }
}
