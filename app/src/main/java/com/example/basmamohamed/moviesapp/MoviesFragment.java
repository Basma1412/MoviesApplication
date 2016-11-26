package com.example.basmamohamed.moviesapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviesFragment extends Fragment {

    public MoviesFragment() {
    }

    private CustomAdapter mMoviesAdapter;
    private List<Movie> movieList = new ArrayList<Movie>();
    private DetailsListener detailsL;
    InternetConnectivity iconnection;
    Boolean isConn;

    void setDetailsListener(DetailsListener detailsL) {
        this.detailsL = detailsL;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        iconnection = new InternetConnectivity(this.getContext());
        isConn = iconnection.isConnected();
        mMoviesAdapter = new CustomAdapter(this.getActivity());
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.grid);
        gridView.setAdapter(mMoviesAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie mmovie = (Movie) mMoviesAdapter.getItem(position);


                int movieId = mmovie.getmId();
                String movieName = mmovie.getTitle();
                String movieOverview = mmovie.getDescription();
                String moviePoster = mmovie.getPoster();
                String movieRating = mmovie.getRating();
                String movieDate = mmovie.getRelease_date();
                if (iconnection.isConnected())
                    detailsL.setMovieDetails(movieId, movieName, movieOverview, moviePoster, movieRating, movieDate);
                else {
                    Toast.makeText(getActivity(), "Please Try connecting to the internet and try again movies frag 1", Toast.LENGTH_LONG).show();
                }


            }
        });
        return rootView;


    }


    @Override
    public void onResume() {
        super.onResume();
        updateOrder();

    }


    private void updateOrder() {


      if  (iconnection.isConnected()) {
          FetchMovies moviesTask = new FetchMovies();
          SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
          String order = prefs.getString(getString(R.string.pref_sort_key),
                  getString(R.string.pref_sort_default));

          if (order.equals("favourites") || (!(iconnection.isConnected()))) {
              if (!isConn && (!(order.equals("favourites"))))
                  Toast.makeText(getActivity(), "It seems that there is no internet, so what about staying with your favorites movies untill you reconnect :D ", Toast.LENGTH_LONG).show();
              DatabaseHelper myDB = new DatabaseHelper(this.getActivity());
              movieList = new ArrayList<Movie>();
              movieList = myDB.getAllMovies();
              mMoviesAdapter.removeAll();
              mMoviesAdapter.addAll(movieList);
          } else {
              moviesTask.execute(order);
          }
      }
        else
      {
          Toast.makeText(getActivity(), "It seems that there is no internet, try reconnecting update order", Toast.LENGTH_LONG).show();

      }
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    public class FetchMovies extends AsyncTask<String, Integer, List<Movie>> {

        private final String LOG_TAG = FetchMovies.class.getSimpleName();



        InternetConnectivity iconnection;
        Boolean isConn;




        @Override
        protected List<Movie> doInBackground(String... params) {



            iconnection = new InternetConnectivity(getContext());

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String MoviesJsonStr = null;

            try {

                String sort_by;
                sort_by = params[0];
                final String APPID_PARAM = "api_key";
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("movie")
                        .appendPath(sort_by)
                        .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_MOVIES_API_KEY);
                URL url = new URL(builder.build().toString());

                if (iconnection.isConnected()) {
                    urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();

                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        MoviesJsonStr = null;
                    }

                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        MoviesJsonStr = null;
                    }
                    MoviesJsonStr = buffer.toString();}

                }
            catch(IOException e){
                    Log.e("MoviesFragment", "Error ", e);
                    MoviesJsonStr = null;
                }finally{
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e("MoviesFragment", "Error closing stream", e);
                        }
                    }
                }


            try {
                 if (iconnection.isConnected())
                 {List<Movie> res = getMoviesDataFormJson(MoviesJsonStr);
                return res;}
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

            return null;
        }


        List<Movie> listOfMovies;
        private List<Movie> getMoviesDataFormJson(String MoviesJsonStr)
                throws JSONException {


            listOfMovies = new ArrayList<>();
            if (iconnection.isConnected())
            {JSONObject response = new JSONObject(MoviesJsonStr);
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                int mid = obj.getInt("id");
                String title = obj.getString("title");
                String poster = obj.getString("poster_path");
                String description = obj.getString("overview");
                String rating = obj.getString("vote_average");
                String release_date = obj.getString("release_date");
                Movie mm = new Movie(mid, title, poster, description, rating, release_date);
                listOfMovies.add(mm);
            }}
            return listOfMovies;


        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(List<Movie> movies) {

            if (movies != null) {
                mMoviesAdapter.removeAll();
                mMoviesAdapter.addAll(movies);

            }

            else
            {
                Toast.makeText(getActivity(), "Please Try connecting to the internet and try again movies async task", Toast.LENGTH_LONG).show();

            }
        }
    }

}