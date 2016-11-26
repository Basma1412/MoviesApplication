package com.example.basmamohamed.moviesapp;


import java.util.List;

import static android.R.id.list;

public class ReviewsAndTrailers {

    List<Review> rev;
    List<Trailer> trails;


    public ReviewsAndTrailers(List<Review> r, List<Trailer> t)
    {
        this.rev=r;
        this.trails=t;
    }


    public List<Review> getRev() {
        return rev;
    }

    public void setRev(List<Review> rev) {
        this.rev = rev;
    }

    public List<Trailer> getTrails() {
        return trails;
    }

    public void setTrails(List<Trailer> trails) {
        this.trails = trails;
    }
}
