package com.alta.rescue;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alta.rescue.BriefFragment.OnListFragmentInteractionListener;
import java.util.ArrayList;

public class BriefRecyclerViewAdapter extends RecyclerView.Adapter<BriefRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Briefing> briefs;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public BriefRecyclerViewAdapter(ArrayList<Briefing> briefs, Context context, OnListFragmentInteractionListener listener) {
        this.briefs = briefs;
        this.context = context;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brief_item, parent, false);
        return new ViewHolder(view);
    }
    private int getColor(int urgency){
        switch (urgency) {
            case 0:
                return android.R.color.holo_green_dark;
            case 1:
                return android.R.color.holo_blue_dark;
            case 2:
                return R.color.yellow;
            case 3:
                return android.R.color.holo_orange_dark;
            case 4:
                return R.color.red;
            default:
                return android.R.color.white;
        }
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.titleView.setText(briefs.get(position).title);
        holder.dateView.setText("Date Posted: "+briefs.get(position).date.toString());
        holder.textView.setText(briefs.get(position).text);
        holder.urgency.setBackgroundColor(ContextCompat.getColor(context,getColor(briefs.get(position).urgency)));
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return briefs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView titleView;
        public final TextView dateView;
        public final TextView textView;
        public final View urgency;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleView = (TextView) view.findViewById(R.id.title);
            dateView = (TextView) view.findViewById(R.id.date);
            textView = (TextView) view.findViewById(R.id.text);
            urgency = (View) view.findViewById(R.id.urgency);

        }
    }
}
