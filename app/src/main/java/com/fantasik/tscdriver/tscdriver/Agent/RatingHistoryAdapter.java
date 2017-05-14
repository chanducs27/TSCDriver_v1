package com.fantasik.tscdriver.tscdriver.Agent;

/**
 * Created by a on 14-May-17.
 */

import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fantasik.tscdriver.tscdriver.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by a on 02-May-17.
 */

public class RatingHistoryAdapter extends RecyclerView.Adapter<RatingHistoryAdapter.MyViewHolder> {
    private RatingHistory[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextname, mtime, mComment;
        public RatingBar ratRating;
        public CircleImageView imgUser;
        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view_ratingHis);
            mTextname= (TextView) v.findViewById(R.id.txtPickName_ratingHis);

            mtime= (TextView) v.findViewById(R.id.txtRatingTime_ratingHis);
            mComment= (TextView) v.findViewById(R.id.txtComment_ratingHis);
            ratRating= (RatingBar) v.findViewById(R.id.ratUserRat_ratingHis);
            imgUser= (CircleImageView) v.findViewById(R.id.imgratingUser_ratingHis);

            // mTextView = (TextView) v.findViewById(R.id.tv_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RatingHistoryAdapter(RatingHistory[] myDataset) {
        mDataset = myDataset;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public RatingHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ratings_card_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextname.setText(mDataset[position].uname);
        holder.mtime.setText(mDataset[position].time);
        holder.mComment.setText(mDataset[position].comment);

        holder.imgUser.setImageBitmap(BitmapFactory.decodeByteArray(mDataset[position].uimage, 0, mDataset[position].uimage.length));
        holder.ratRating.setRating(Float.parseFloat(mDataset[position].rate));
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}