package com.example.karankatyal.moviemania;

/**
 * Created by Karan Katyal on 06-10-2016.
 */
public class ListViewMovie {

    String title;
    String year;
    String rating;
    String poster;

    public ListViewMovie(String title, String year, String rating, String poster) {
        this.title = title;
        this.year = year;
        this.rating = rating;
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String getPoster() {
        return poster;
    }
}

