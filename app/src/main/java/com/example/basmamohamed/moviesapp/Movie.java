package com.example.basmamohamed.moviesapp;

import static android.R.attr.rating;

/**
 * Created by Basma Mohamed on 10/27/2016.
 */

public class Movie {
    int mId;
    String poster;
    String description;
    String title;

    String rating;
    String release_date;

    public Movie(int mId,String t,String p , String d,String rating,String release_date)
    {
        this.mId=mId;
        poster=p;
        description=d;
        title=t;
        this.rating=rating;
        this.release_date=release_date;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getPoster() {
        return poster;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
