package com.example.moviestar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private Button buttonSearch;
    private ListView listViewResults;
    private ArrayAdapter<String> adapter;
    private List<String> movieTitles;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        listViewResults = findViewById(R.id.listViewResults);

        movieTitles = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieTitles);
        listViewResults.setAdapter(adapter);

        movieList = new ArrayList<>();

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Clear previous search results
                movieTitles.clear();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        listViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Movie selectedMovie = movieList.get(position);
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra("title", selectedMovie.getTitle());
                intent.putExtra("poster_path", selectedMovie.getPosterPath());
                intent.putExtra("release_date", selectedMovie.getReleaseDate());
                intent.putExtra("rating", selectedMovie.getRating());
                intent.putExtra("overview", selectedMovie.getOverview());
                startActivity(intent);
            }
        });
    }

    public void onSearchClicked(View view) {
        String searchTerm = editTextSearch.getText().toString().trim();
        if (!searchTerm.isEmpty()) {
            new FetchMovieDataTask().execute(searchTerm);
        } else {
            Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show();
        }
    }

    private class FetchMovieDataTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            String searchTerm = strings[0];
            List<Movie> results = new ArrayList<>();

            try {
                // Construct the URL for the API request
                String apiKey = "e596aa31a0ad49bb7a02a71c3e788e69";
                String apiUrl = "https://api.themoviedb.org/3/search/movie";
                String query = URLEncoder.encode(searchTerm, "UTF-8");

                URL url = new URL(apiUrl + "?api_key=" + apiKey + "&query=" + query);

                // Open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    // Read the response into a String
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Parse the JSON response
                    JSONObject response = new JSONObject(result.toString());
                    JSONArray movies = response.getJSONArray("results");

                    // Extract movie details
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);
                        String title = movie.getString("title");
                        String posterPath = movie.getString("poster_path");
                        String releaseDate = movie.getString("release_date");
                        double rating = movie.getDouble("vote_average");
                        String overview = movie.getString("overview");
                        results.add(new Movie(title, posterPath, releaseDate, rating, overview));
                    }
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return results;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            super.onPostExecute(result);
            // Update UI with fetched data
            movieList.clear();
            movieList.addAll(result);

            // Update list view with movie titles
            movieTitles.clear();
            for (Movie movie : result) {
                movieTitles.add(movie.getTitle());
            }
            adapter.notifyDataSetChanged();
        }
    }
}
