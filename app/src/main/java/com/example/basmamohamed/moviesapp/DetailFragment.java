package com.example.basmamohamed.moviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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


public class DetailFragment extends Fragment {


    public DetailFragment() {
    }


    ImageButton deleteBtn;
    DatabaseHelper myDB;
    ImageButton fav;
    int id;
    int movie_Id;
    String MovieName, moviePoster, movieRating, movie_Release_date, movie_OverView;

    private TrailerAdapter tTrailersAdapter;
    private ReviewsAdapter rReviewsAdapter;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle intent = getBundle();
        fav = (ImageButton) rootView.findViewById(R.id.favbtn);
        deleteBtn = (ImageButton) rootView.findViewById(R.id.removeBtn);
        myDB = new DatabaseHelper(this.getActivity());
        if (intent != null) {

            MovieName = intent.getString("MovieName");
            id = intent.getInt("mId");
            movie_Id = id;

            movie_OverView = intent.getString("Movie OverView");
            movieRating = intent.getString("MovieRating");
            movie_Release_date = intent.getString("Movie_Release_date");
            moviePoster = intent.getString("MoviePoster");


            ((TextView) rootView.findViewById(R.id.detail_title)).setText(MovieName);
            Picasso.with(this.getContext()).load("http://image.tmdb.org/t/p/w185/" + moviePoster).into((ImageView) rootView.findViewById(R.id.detail_poster));
            ((TextView) rootView.findViewById(R.id.detail_overview)).setText(movie_OverView);
            ((TextView) rootView.findViewById(R.id.detail_review)).setText(movieRating);
            ((TextView) rootView.findViewById(R.id.detail_date)).setText(movie_Release_date);


        }


        ListView listView = (ListView) rootView.findViewById(R.id.reviews_list);
        GridView gridView = (GridView) rootView.findViewById(R.id.trailers_grid);
        rReviewsAdapter = new ReviewsAdapter(this.getActivity());
        tTrailersAdapter = new TrailerAdapter(this.getActivity());
        listView.setAdapter(rReviewsAdapter);
        gridView.setAdapter(tTrailersAdapter);




        listView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {


                int touched = event.getAction();
                switch (touched) {
                    case MotionEvent.ACTION_DOWN:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                view.onTouchEvent(event);
                return true;


            }
        });



        gridView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {


                int touched = event.getAction();
                switch (touched) {
                    case MotionEvent.ACTION_DOWN:
                        view.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                view.onTouchEvent(event);
                return true;


            }
        });


        AddData();
        deleteData();

        return rootView;
    }




    private Bundle getBundle() {
        Bundle bundle = getArguments();
        if (bundle == null)
            bundle = getActivity().getIntent().getExtras();
        return bundle;
    }


    @Override
    public void onResume() {
        super.onResume();
        FetchReviews fetchReviews = new FetchReviews();
        String movieID = movie_Id + "";
        fetchReviews.execute(movieID);


    }


    public void AddData() {
        fav.setOnClickListener(
                new View.OnClickListener()

                {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData(id, MovieName, movie_OverView, moviePoster, movieRating, movie_Release_date);
                        if (isInserted) {
                            Toast.makeText(getActivity(), "Movie was added to favourite", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getActivity(), "Movie was not added to favourite,You may have added it before ", Toast.LENGTH_LONG).show();

                        }
                    }

                });
    }


    public void deleteData() {
        deleteBtn.setOnClickListener(
                new View.OnClickListener()

                {
                    @Override
                    public void onClick(View v) {

                        Integer deleteRows = myDB.deleteData(id + "");
                        if (deleteRows > 0) {
                            Toast.makeText(getActivity(), "Movie was deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Movie was not deleted", Toast.LENGTH_LONG).show();

                        }
                    }

                });
    }


    public class FetchReviews extends AsyncTask<String, Integer, ReviewsAndTrailers> {

        private final String LOG_TAG = FetchReviews.class.getSimpleName();


        InternetConnectivity iconnection;
        Boolean isConn;


        @Override
        protected ReviewsAndTrailers doInBackground(String... params) {

            iconnection = new InternetConnectivity(getContext());
            ReviewsAndTrailers randt;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String ReviewsJsonStr = null;
            HttpURLConnection urlConnection2 = null;

            BufferedReader reader2 = null;
            String ReviewsJsonStr2 = null;

            String movie_id;
            movie_id = params[0];

            final String APPID_PARAM = "api_key";

            try {

                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("movie")
                        .appendPath(movie_id)
                        .appendPath("reviews")
                        .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_MOVIES_API_KEY);
                URL url = new URL(builder.build().toString());
                if (iconnection.isConnected()) {

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        ReviewsJsonStr = null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        ReviewsJsonStr = null;
                    }
                    ReviewsJsonStr = buffer.toString();
                }
            }
            catch (IOException e) {
                Log.e("ReviewsFragment", "Error ", e);
                ReviewsJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("ReviewsFragment", "Error closing stream", e);
                    }
                }
            }


            try {

                /********************************************/

                Uri.Builder builder2 = new Uri.Builder();
                builder2.scheme("https")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("movie")
                        .appendPath(movie_id)
                        .appendPath("videos")
                        .appendQueryParameter(APPID_PARAM, BuildConfig.OPEN_MOVIES_API_KEY);
                URL url2 = new URL(builder2.build().toString());


                if (iconnection.isConnected())
                {
                urlConnection2 = (HttpURLConnection) url2.openConnection();
                urlConnection2.setRequestMethod("GET");
                urlConnection2.connect();

                InputStream inputStream2 = urlConnection2.getInputStream();
                StringBuffer buffer2 = new StringBuffer();
                if (inputStream2 == null) {
                    ReviewsJsonStr2 = null;
                }
                reader2 = new BufferedReader(new InputStreamReader(inputStream2));

                String line2;
                while ((line2 = reader2.readLine()) != null) {
                    buffer2.append(line2 + "\n");
                }

                if (buffer2.length() == 0) {
                    ReviewsJsonStr2 = null;
                }
                ReviewsJsonStr2 = buffer2.toString();
            } }catch (IOException e) {
                Log.e("ReviewsFragment", "Error ", e);
                ReviewsJsonStr2 = null;

            } finally {
                if (urlConnection2 != null) {
                    urlConnection2.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("ReviewsFragment", "Error closing stream", e);
                    }
                }
            }


            /********************************************/


            List<Review> r;
            List<Trailer> t;

            try {
                if (iconnection.isConnected())
                {
                r = getReviewsDataFormJson(ReviewsJsonStr);
                t = getTrailersDataFormJson(ReviewsJsonStr2);
                randt = new ReviewsAndTrailers(r, t);
                return randt;
            } }catch (JSONException e) {
                e.printStackTrace();
                return null;
            }


            return null;

        }


        List<Review> listOfReviews;

        private List<Review> getReviewsDataFormJson(String ReviewsJson)
                throws JSONException {

            if (iconnection.isConnected())
            {
            JSONObject response = new JSONObject(ReviewsJson);
            JSONArray results = response.getJSONArray("results");


            listOfReviews = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                String rid = obj.getString("id");
                String author = obj.getString("author");
                String content = obj.getString("content");
                Review mm = new Review(rid, author, content);
                listOfReviews.add(mm);
            }}
            return listOfReviews;


        }


        List<Trailer> listOfTrailers;

        private List<Trailer> getTrailersDataFormJson(String ReviewsJson)
                throws JSONException {

            if (iconnection.isConnected())
            {
            JSONObject response = new JSONObject(ReviewsJson);
            JSONArray results = response.getJSONArray("results");


            listOfTrailers = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                String name = obj.getString("name");
                String key = obj.getString("key");
                Trailer tt = new Trailer(key, name);
                listOfTrailers.add(tt);
            }}
            return listOfTrailers;


        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onPostExecute(ReviewsAndTrailers rt) {


            List<Review> revs = rt.getRev();
            List<Trailer> trl = rt.getTrails();
            if ((rt != null)||(revs != null))
            {     if(revs != null) {
                rReviewsAdapter.removeAll();
                rReviewsAdapter.addAll(revs);
            }

                if (rt != null) {
                    tTrailersAdapter.removeAll();
                    tTrailersAdapter.addAll(trl);

                }
            }
              else
                {
                    Toast.makeText(getActivity(), "Please Try connecting to the internet and try again", Toast.LENGTH_LONG).show();

                }
            }

        }



}