package com.example.karankatyal.moviemania;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Karan Katyal on 05-10-2016.
 */
public class MovieDetailTask extends AsyncTask<String,Void,Movie> {

    @Override
    protected Movie doInBackground(String... params) {

        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(params[0])).openConnection();

            conn.connect();

            String content = connToString(conn.getInputStream());


            Movie movie = jsonToMovie(content);

            return movie;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Movie jsonToMovie(String content) throws JSONException {
        Movie movie = new Movie();
        JSONObject jo = new JSONObject(content);

        movie.setImage(jo.getString("Poster"));
        movie.setTitle(jo.getString("Title"));
        movie.setCast(jo.getString("Actors"));
        movie.setPlot(jo.getString("Plot"));

        return movie;
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

