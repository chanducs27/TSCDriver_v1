package com.fantasik.tscdriver.tscdriver.Agent;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fantasik.tscdriver.tscdriver.R;

/**
 * Created by a on 02-May-17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private TripHistory[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextname,mTextCash, mTextPaymentmode;
        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextname= (TextView) v.findViewById(R.id.txtPickName);
            mTextCash= (TextView) v.findViewById(R.id.txtPickCost);
            mTextPaymentmode= (TextView) v.findViewById(R.id.txtPickModeCash);

           // mTextView = (TextView) v.findViewById(R.id.tv_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(TripHistory[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       holder.mTextname.setText(mDataset[position].uname);
        holder.mTextPaymentmode.setText(mDataset[position].paymentmode);
        holder.mTextCash.setText(mDataset[position].cost);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}