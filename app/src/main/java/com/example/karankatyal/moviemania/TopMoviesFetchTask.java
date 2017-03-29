package com.example.karankatyal.moviemania;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Karan Katyal on 07-10-2016.
 */
public class TopMoviesFetchTask extends AsyncTask<String,Void,ArrayList<TopMovie>> {

    public static ArrayList<TopMovie>  topMovies = new ArrayList<>();

    @Override

    protected ArrayList<TopMovie> doInBackground(String... params) {

        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(params[0])).openConnection();

            conn.connect();

            String content = connToString(conn.getInputStream());


            topMovies = jsonToMovie(content);

            return topMovies;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<TopMovie> jsonToMovie(String jsonString) throws JSONException {

        JSONArray JArr = new JSONArray(jsonString);

        for (int i = 0; i < JArr.length(); i++) {

            JSONObject topMoviesObj = JArr.getJSONObject(i);
            String topPoster = topMoviesObj.getString("PosterPath");
            String topTitle = topMoviesObj.getString("Title");
            String topRating = topMoviesObj.getString("Rating");
            String topDescription = topMoviesObj.getString("Description");
            String topTrailerLink = topMoviesObj.getString("TrailerLink");

            topMovies.add(new TopMovie(topPoster,topTitle,topRating,
                    topDescription,topTrailerLink));

        }

        return topMovies;
    }

    private String connToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String buffer = "";

        while (buffer != null) {
            buffer = br.readLine();
            sb.append(buffer);
        }
        return sb.toString();
    }
}