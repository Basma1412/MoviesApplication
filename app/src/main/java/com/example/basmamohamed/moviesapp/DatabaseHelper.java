package com.example.basmamohamed.moviesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.basmamohamed.moviesapp.MoviesContract.MoviesEntry;
import com.example.basmamohamed.moviesapp.MoviesContract.IdEntry;

import java.util.ArrayList;

import static android.R.attr.name;
import static android.R.attr.version;
import static com.example.basmamohamed.moviesapp.MoviesContract.MoviesEntry.Col_date;
import static com.example.basmamohamed.moviesapp.MoviesContract.MoviesEntry.Col_description;
import static com.example.basmamohamed.moviesapp.MoviesContract.MoviesEntry.Col_mId;
import static com.example.basmamohamed.moviesapp.MoviesContract.MoviesEntry.Col_poster;
import static com.example.basmamohamed.moviesapp.MoviesContract.MoviesEntry.Col_rate;
import static com.example.basmamohamed.moviesapp.MoviesContract.MoviesEntry.Col_title;
import static com.example.basmamohamed.moviesapp.MoviesContract.MoviesEntry.TABLE_NAME;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Movies.db";
    private static final int DATABASE_VERSION = 2;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MoviesEntry.TABLE_NAME + " (" +
                MoviesEntry.Col_Id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Col_mId + " INTEGER NOT NULL , " +
                Col_title + " TEXT NOT NULL , " +
                Col_description + " TEXT NOT NULL, " +
                Col_poster + " TEXT NOT NULL, " +
                Col_rate + " TEXT NOT NULL, " +
                Col_date + " TEXT NOT NULL,UNIQUE(" + Col_title + "));";
        db.execSQL(SQL_CREATE_MOVIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
        onCreate(db);
    }


    SQLiteDatabase db;

    public boolean insertData(Integer mId, String title, String description, String poster, String rate, String date) {
        db = this.getWritableDatabase();
        if (getCount(mId) == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Col_mId, mId);
            contentValues.put(Col_title, title);
            contentValues.put(Col_description, description);
            contentValues.put(Col_poster, poster);
            contentValues.put(Col_rate, rate);
            contentValues.put(Col_date, date);


            long result = db.insert(MoviesEntry.TABLE_NAME, null, contentValues);
            if (result == -1) return false;
            else
                return true;


        } else return false;
    }


    public int getCount(Integer mId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + MoviesEntry.TABLE_NAME + " where " + MoviesEntry.Col_mId + " = " + mId, null);
        int cnt = res.getCount();
        return cnt;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + MoviesEntry.TABLE_NAME, null);
        return res;

    }


    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(MoviesEntry.TABLE_NAME, MoviesEntry.Col_mId + "= ?", new String[]{id});
    }

    public ArrayList<Movie> getAllMovies() {
        String query = "SELECT * FROM " + MoviesEntry.TABLE_NAME;
        ArrayList<Movie> movies = new ArrayList<Movie>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(query, null);
        if (c != null) {
            while (c.moveToNext()) {
                int mId = c.getInt(c.getColumnIndex(Col_mId));
                String poster = c.getString(c.getColumnIndex(Col_poster));
                String description = c.getString(c.getColumnIndex(Col_description));
                String title = c.getString(c.getColumnIndex(Col_title));
                String rating = c.getString(c.getColumnIndex(Col_rate));
                String release_date = c.getString(c.getColumnIndex(Col_date));
                Movie movie = new Movie(mId, title, poster, description, rating, release_date);

                movies.add(movie);
            }
        }
        return movies;
    }
}


