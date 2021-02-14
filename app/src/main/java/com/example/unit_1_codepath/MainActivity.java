package com.example.unit_1_codepath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.unit_1_codepath.adapters.MovieAdapter;
import com.example.unit_1_codepath.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String Playing = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String Tag = "MainActivity";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Playing, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(Tag, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(Tag, "Results:" + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(Tag, "Movies: " + movies.size());
                } catch (JSONException e) {
                    Log.e(Tag, "Hit json exception", e);

                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(Tag, "onFailure");
            }
        });

        /*
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Playing, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(Tag, "Results");
                JSONObject jsonObject = json.jsonObject;
                try{
                    JSONArray results = jsonObject.getJSONArray(name: "results");
                    Log.i(Tag, msg: "Results: " + results.toString());

                }
                catch (JSONException e){
                    Log.e(Tag, msg: "Hit json exception", e);
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable){
                Log.d(Tag, msg: "onFailure");
            }
        });
 */
    }

}

