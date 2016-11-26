package com.example.basmamohamed.moviesapp;

public class Review {


    String mId;

    String author;
    String description;


    public Review(String mId,String t,String d)
    {
        this.mId=mId;
        description=d;
        author =t;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
