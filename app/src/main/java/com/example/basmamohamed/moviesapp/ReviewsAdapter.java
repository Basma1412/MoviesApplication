package com.example.basmamohamed.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;



public class ReviewsAdapter extends BaseAdapter {


    List<Review> reviews;
    Context mContext;

    public ReviewsAdapter()
    {
        reviews=new ArrayList<>();

    }

    public ReviewsAdapter(Context c)
    {
        reviews=new ArrayList<>();
        mContext=c;
    }


    public ReviewsAdapter(List<Review> reviews,Context c)
    {
        this.reviews=reviews;
        mContext=c;
    }

    public void add(Review review)
    {
        reviews.add(review);
    }


    public void addAll(List <Review> reviewList)
    {
        reviews.addAll(reviewList);
        this.notifyDataSetChanged();
    }


    public void removeAll()
    {
        reviews.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Review getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mview=convertView;
        ReviewsAdapter.ViewHolder viewHolder;
        if (mview == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mview =inflater.inflate(R.layout.review_single,null);
            viewHolder = new ReviewsAdapter.ViewHolder(mview);
            mview.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ReviewsAdapter.ViewHolder) mview.getTag();
        }
        viewHolder.reviewSummary.setText(reviews.get(position).getAuthor());;
        viewHolder.reviewDescription.setText(reviews.get(position).getDescription());;

        return mview;

    }

    public class ViewHolder
    {
        TextView reviewSummary;
        TextView reviewDescription;

        public ViewHolder(View mview)
        {

            reviewSummary=(TextView) mview.findViewById(R.id.review_summary);
            reviewDescription=(TextView) mview.findViewById(R.id.review_content);

        }
    }

}
