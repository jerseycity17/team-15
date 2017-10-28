package com.alta.rescue;

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

    public BriefRecyclerViewAdapter(ArrayList<Briefing> briefs, OnListFragmentInteractionListener listener) {
        this.briefs = briefs;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brief_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.titleView.setText("Title: "+briefs.get(position).title);
        holder.dateView.setText("Date: "+briefs.get(position).date.toString());
        holder.textView.setText("Text: "+briefs.get(position).text);
        holder.urgencyView.setText("Urgency: "+briefs.get(position).urgency.toString());

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
        public final TextView urgencyView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            titleView = (TextView) view.findViewById(R.id.title);
            dateView = (TextView) view.findViewById(R.id.date);
            textView = (TextView) view.findViewById(R.id.text);
            urgencyView = (TextView) view.findViewById(R.id.urgency);

        }
    }
}
