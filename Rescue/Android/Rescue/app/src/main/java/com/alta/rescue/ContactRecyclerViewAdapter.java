package com.alta.rescue;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alta.rescue.ContactFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;


public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private ArrayList<Contact> contacts;
    private Context context;

    public ContactRecyclerViewAdapter(ArrayList<Contact> contacts, Context context, OnListFragmentInteractionListener listener) {
        mListener = listener;
        this.contacts = contacts;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mIdView.setText(contacts.get(position).getName());
        holder.mContentView.setText(contacts.get(position).getOccupation());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("tel", contacts.get(position).getPhone(), null));
                context.startActivity(intent);

            }
        });

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", contacts.get(position).getPhone(), null));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageButton call;
        public final ImageButton text;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            call = (ImageButton) view.findViewById(R.id.call);
            text = (ImageButton) view.findViewById(R.id.message);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
