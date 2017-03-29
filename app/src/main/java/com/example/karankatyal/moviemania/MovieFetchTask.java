package com.example.karankatyal.moviemania;

import android.os.AsyncTask;
import android.util.Log;

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
 * Created by Karan Katyal on 05-10-2016.
 */
public class MovieFetchTask extends AsyncTask<String, Void, ArrayList<ListViewMovie>> {

    private static final String TAG ="YYY" ;

    public static ArrayList<ListViewMovie> movieList = new ArrayList<>();

    @Override
    protected ArrayList<ListViewMovie> doInBackground(String... params) {
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(params[0])).openConnection();

            conn.connect();

            String content = connToString(conn.getInputStream());

            Log.d(TAG,"content :"+content);

            movieList = jsonToEventArray(content);

            return movieList;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<ListViewMovie> jsonToEventArray(String jsonString) throws JSONException {

        JSONObject jo = new JSONObject(jsonString);

        JSONArray jArr = jo.getJSONArray("Search");

        for (int i = 0; i < jArr.length(); i++) {
            JSONObject eventObj = jArr.getJSONObject(i);
            String movieName = eventObj.getString("Title");
            String year=eventObj.getString("Year");
            String rating=eventObj.getString("Type");
            String poster=eventObj.getString("Poster");
            movieList.add(new ListViewMovie(movieName,year,rating,poster));
        }
        return movieList;
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

