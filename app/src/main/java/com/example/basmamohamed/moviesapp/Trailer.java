package com.example.basmamohamed.moviesapp;

import static android.R.attr.author;
import static android.R.attr.description;

/**
 * Created by Basma Mohamed on 11/24/2016.
 */

public class Trailer {

        String trailerName;
        String trailerLink;



        public Trailer(String trailerLink,String trailerName)
        {
            this.trailerLink=trailerLink;
            this.trailerName=trailerName;


        }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public String getTrailerLink() {
        return trailerLink;
    }

    public void setTrailerLink(String trailerLink) {
        this.trailerLink = trailerLink;
    }
}
