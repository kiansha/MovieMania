package com.example.karankatyal.moviemania;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "mmm";

    EditText etSearch;
    ImageView play;
    ImageView fav;
    RecyclerView rvTopMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search1);

        etSearch = (EditText) findViewById(R.id.et_search);
        play=(ImageView) findViewById(R.id.play);
        fav= (ImageView) findViewById(R.id.favourites);
        rvTopMovies= (RecyclerView) findViewById(R.id.rv_topMovies);

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentFocus();
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if(checkInternet()) {
                        Intent  i = new Intent(SearchActivity.this,ResultActivity.class);
                        i.putExtra("keyword", etSearch.getText().toString());
                        startActivity(i);
                    }
                    handled = true;
                }
                return handled;
            }
        });



        if (checkInternet()){
            new TopMoviesFetchTask(){

                @Override
                protected void onPostExecute(ArrayList<TopMovie> topMovies) {
                    super.onPostExecute(topMovies);

                    TopMovieViewAdapter topMovieViewAdapter= new TopMovieViewAdapter();
                    LinearLayoutManager lm= new LinearLayoutManager(SearchActivity.this,
                            LinearLayoutManager.HORIZONTAL,false);
                    rvTopMovies.setLayoutManager(lm);
                    rvTopMovies.setAdapter(topMovieViewAdapter);
                }
            }.execute("http://api.cinemalytics.in/v2/analytics/TopMovies/?auth_token=");
        }
    }
    public boolean checkInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            return true;
        }
        else
        {
            Toast.makeText(SearchActivity.this,
                    "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public class TopMovieViewHolder extends RecyclerView.ViewHolder {

        public TopMovieViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;

            poster = (ImageView) itemView.findViewById(R.id.topPoster);
        }

        View rootView;
        ImageView poster;
    }

    public class TopMovieViewAdapter extends RecyclerView.Adapter<TopMovieViewHolder> {

        @Override
        public TopMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater li = getLayoutInflater();
            View convertView ;

            convertView = li.inflate(R.layout.dummy, parent, false);

            TopMovieViewHolder topMovieViewHolder = new TopMovieViewHolder(convertView);

            return topMovieViewHolder;
        }

        @Override
        public void onBindViewHolder(TopMovieViewHolder topMovieViewHolder, final int position) {

            TopMovie lvm = TopMoviesFetchTask.topMovies.get(position);

            Picasso.with(SearchActivity.this)
                    .load(lvm.getTopPoster())
                    .error(R.drawable.noimagefound)
                    .into(topMovieViewHolder.poster);
        }
        @Override
        public int getItemCount() {

            return TopMoviesFetchTask.topMovies.size();
        }
    }
}

