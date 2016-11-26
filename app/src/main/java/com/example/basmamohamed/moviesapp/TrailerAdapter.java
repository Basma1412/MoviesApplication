package com.example.basmamohamed.moviesapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;


public class TrailerAdapter extends BaseAdapter {


    List<Trailer> trailers;
    Context mContext;

    public TrailerAdapter() {
        trailers = new ArrayList<>();

    }

    public TrailerAdapter(Context c) {
        trailers = new ArrayList<>();
        mContext = c;
    }


    public TrailerAdapter(List<Trailer> trailers, Context c) {
        this.trailers = trailers;
        mContext = c;
    }

    public void add(Trailer trailer) {
        trailers.add(trailer);
    }


    public void addAll(List<Trailer> trailerList) {
        trailers.addAll(trailerList);
        this.notifyDataSetChanged();
    }


    public void removeAll() {
        trailers.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public Trailer getItem(int position) {
        return trailers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mview = convertView;
        TrailerAdapter.ViewHolder viewHolder;
        if (mview == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mview = inflater.inflate(R.layout.trailer_single, null);
            viewHolder = new TrailerAdapter.ViewHolder(mview);
            mview.setTag(viewHolder);
        } else {
            viewHolder = (TrailerAdapter.ViewHolder) mview.getTag();
        }

        viewHolder.trailerName.setText(trailers.get(position).getTrailerName());

        final String linkOfTrailer = trailers.get(position).getTrailerLink();
        viewHolder.trailerVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + linkOfTrailer));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + linkOfTrailer));
                try {
                    mContext.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    mContext.startActivity(webIntent);
                }
            }

        });
        return mview;

    }

    public class ViewHolder {

        ImageButton trailerVideo;
        TextView trailerName;


        public ViewHolder(View mview) {

            trailerVideo = (ImageButton) mview.findViewById(R.id.videoTrailer);
            trailerName = (TextView) mview.findViewById(R.id.trailer_name);


        }
    }


}
