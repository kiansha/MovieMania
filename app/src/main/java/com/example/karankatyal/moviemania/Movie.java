package com.example.karankatyal.moviemania;

/**
 * Created by Karan Katyal on 05-10-2016.
 */
public class Movie  {

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getTitle() {

        return title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String image;
    String title;
    String cast;
    String plot;

}
