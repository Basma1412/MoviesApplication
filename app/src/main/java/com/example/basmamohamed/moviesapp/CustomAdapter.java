package com.example.basmamohamed.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends BaseAdapter {

    List <Movie> movies;
    Context mContext;

    public CustomAdapter()
    {
        movies=new ArrayList<>();

    }

    public CustomAdapter(Context c)
    {
        movies=new ArrayList<>();
        mContext=c;
    }


    public CustomAdapter(List<Movie> movies,Context c)
    {
        this.movies=movies;
        mContext=c;
    }

    public void add(Movie movie)
    {
        movies.add(movie);
    }


    public void addAll(List <Movie> movieList)
    {
        movies.addAll(movieList);
        this.notifyDataSetChanged();
    }


    public void removeAll()
    {
        movies.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mview=convertView;
        ViewHolder viewHolder;
        if (mview == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mview =inflater.inflate(R.layout.grid_single,null);
            viewHolder = new ViewHolder(mview);
            mview.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) mview.getTag();
        }
        viewHolder.movieName.setText(movies.get(position).getTitle());;
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/"+movies.get(position).getPoster()).into(viewHolder.moviePoster);

        return mview;

    }

    public class ViewHolder
    {
        ImageView moviePoster;
        TextView movieName;


        public ViewHolder(View mview)
        {

            movieName=(TextView) mview.findViewById(R.id.grid_title);
            moviePoster = (ImageView)mview.findViewById(R.id.grid_image);

        }
    }
}