package com.example.basmamohamed.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements DetailsListener {


    boolean  isBig=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MoviesFragment moviesFragment=new MoviesFragment();
        moviesFragment.setDetailsListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,moviesFragment,"").commit();
        if(null!=findViewById(R.id.movie_detail_container))
        {
            isBig=true;


        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setMovieDetails(int Id, String name, String overview, String poster, String rating, String date) {

        InternetConnectivity iconnection;
        iconnection = new InternetConnectivity(this);
        if (isBig)
        {
            DetailFragment detailsFrag=new DetailFragment();
            Bundle extras=new Bundle();
            extras.putInt("mId", Id);
            extras.putString("MovieName", name);
            extras.putString("Movie OverView",overview);
            extras.putString("MoviePoster", poster);
            extras.putString("MovieRating", rating);
            extras.putString("Movie_Release_date", date);
            detailsFrag.setArguments(extras);
            if (iconnection.isConnected())
            {getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container,detailsFrag,"").commit();}
            else {
                Toast.makeText(this, "Connect to the internet then try again ", Toast.LENGTH_LONG).show();
            }
        }
        else
        {

                Intent intent = new Intent(this, DetailActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("mId", Id);
                extras.putString("MovieName", name);
                extras.putString("Movie OverView",overview);
                extras.putString("MoviePoster", poster);
                extras.putString("MovieRating", rating);
                extras.putString("Movie_Release_date", date);
                intent.putExtras(extras);
            if(iconnection.isConnected())
            { startActivity(intent);}
            else {
                Toast.makeText(this, "Connect to the internet then try again ", Toast.LENGTH_LONG).show();
            }

        }



    }
}
