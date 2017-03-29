package com.example.karankatyal.moviemania;

/**
 * Created by Karan Katyal on 07-10-2016.
 */
public class TopMovie {

    String TopPoster;
    String TopTitle;
    String TopRating;
    String TopDescription;
    String TopTrailerLink;

    public TopMovie(String topPoster, String topTitle, String topRating, String topDescription, String topTrailerLink) {
        TopPoster = topPoster;
        TopTitle = topTitle;
        TopRating = topRating;
        TopDescription = topDescription;
        TopTrailerLink = topTrailerLink;
    }

    public String getTopPoster() {
        return TopPoster;
    }

    public String getTopTitle() {
        return TopTitle;
    }

    public String getTopRating() {
        return TopRating;
    }

    public String getTopDescription() {
        return TopDescription;
    }

    public String getTopTrailerLink() {
        return TopTrailerLink;
    }
}
