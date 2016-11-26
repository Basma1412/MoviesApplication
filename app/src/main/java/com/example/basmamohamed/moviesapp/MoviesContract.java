package com.example.basmamohamed.moviesapp;

import android.provider.BaseColumns;


public class MoviesContract {

    public static final class IdEntry implements BaseColumns {
        public static final String TABLE_NAME = "movieId";

    }

    public static final class MoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String Col_Id = "ID";
        public static final String Col_mId = "MOVIE_ID";
        public static final String Col_poster = "poster";
        public static final String Col_description = "description";
        public static final String Col_title = "title";
        public static final String Col_rate = "rating";
        public static final String Col_date = "release_date";

    }
}
