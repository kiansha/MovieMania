package com.example.karankatyal.moviemania;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Karan Katyal on 05-10-2016.
 */
public class ResultActivity extends AppCompatActivity {


    RecyclerView rv_movies;
    Intent gotIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        gotIntent=getIntent();
        String key_word = gotIntent.getStringExtra("keyword");
        //"http://www.omdbapi.com/?s="+key_word

        rv_movies= (RecyclerView) findViewById(R.id.rv_listMovies);

    ////    apiUrl = apiUrl + key_word;

        if (checkInternet()) {    //necessary????
            new MovieFetchTask() {
                @Override
                protected void onPostExecute(ArrayList<ListViewMovie> listViewMovies) {

                    MovieViewAdapter movieAdapter = new MovieViewAdapter();
                    LinearLayoutManager lm = new LinearLayoutManager(ResultActivity.this);
                    rv_movies.setLayoutManager(lm);
                    rv_movies.setAdapter(movieAdapter);

                    super.onPostExecute(listViewMovies);
                }
            }.execute("http://www.omdbapi.com/?s="+key_word);
        }
    }
    public boolean checkInternet(){   //necessary??

        ConnectivityManager cm=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni=cm.getActiveNetworkInfo();
        if(ni!=null&&ni.isConnected()){
            return true;
        }
        return false;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public MovieViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            title = (TextView) itemView.findViewById(R.id.rv_title);
            year = (TextView) itemView.findViewById(R.id.rv_year);
            imbdRating = (TextView) itemView.findViewById(R.id.rv_imbdRating);
            poster = (ImageView) itemView.findViewById(R.id.rv_poster);
        }

        View rootView;
        TextView title;
        TextView year;
        TextView imbdRating;
        ImageView poster;
    }

    public class MovieViewAdapter extends RecyclerView.Adapter<MovieViewHolder> {

        @Override
        public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater li = getLayoutInflater();
            View convertView ;

            convertView = li.inflate(R.layout.activity_result_rvinflate, parent, false);

            MovieViewHolder movieViewHolder = new MovieViewHolder(convertView);

            return movieViewHolder;
        }

        @Override
        public void onBindViewHolder(MovieViewHolder movieViewHolder, final int position) {

            ListViewMovie lvm = MovieFetchTask.movieList.get(position);

            movieViewHolder.title.setText(lvm.getTitle());
            movieViewHolder.year.setText(lvm.getYear());
            movieViewHolder.imbdRating.setText(lvm.getRating());

            Picasso.with(ResultActivity.this)
                    .load(lvm.getPoster())
                    .error(R.drawable.noimagefound)
                    .into(movieViewHolder.poster);

            movieViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent =new Intent(ResultActivity.this,DetailActivity.class);
                    intent.putExtra("movie", MovieFetchTask.movieList.get(position).getTitle());
                    startActivity(intent);
                }
            });
        }
        @Override
        public int getItemCount() {

            return MovieFetchTask.movieList.size();
        }
    }

}
