package com.example.karankatyal.moviemania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    Intent gotIntent;
    TextView title,plot,actor;
    ImageView movieImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = (TextView)findViewById(R.id.title);
        actor = (TextView)findViewById(R.id.actor);
        plot = (TextView)findViewById(R.id.plot);
        movieImage = (ImageView)findViewById(R.id.images);

        gotIntent=getIntent();
        String movieName = gotIntent.getStringExtra("movie");
        Toast.makeText(DetailActivity.this,movieName, Toast.LENGTH_SHORT).show();
        new MovieDetailTask() {
            @Override
            protected void onPostExecute(Movie movie) {
                super.onPostExecute(movie);

                title.setText(movie.getTitle());
                actor.setText(movie.getCast());
                plot.setText(movie.getPlot());

                Picasso.with(DetailActivity.this)
                        .load(movie.getImage())
                        .error(R.drawable.noimagefound)
                        .into(movieImage);
            }
        }.execute("http://www.omdbapi.com/?t="+movieName.replace(' ', '+' ));
    }
}
