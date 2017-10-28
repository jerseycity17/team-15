package com.alta.rescue;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alta.rescue.ContactFragment.OnListFragmentInteractionListener;
import com.alta.rescue.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;


public class MyContactRecyclerViewAdapter extends RecyclerView.Adapter<MyContactRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private ArrayList<Contact> contacts;
    private Context context;

    public MyContactRecyclerViewAdapter(ArrayList<Contact> contacts, Context context, OnListFragmentInteractionListener listener) {
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
        holder.mIdView.setText(contacts.get(position).name);
        holder.mContentView.setText(contacts.get(position).occupation);

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
                Intent intent = new Intent(Intent.ACTION_NEW_OUTGOING_CALL, Uri.fromParts("tel", contacts.get(position).phone.toString(), null));
                context.startActivity(intent);

            }
        });

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                Intent intent = new Intent(Intent.ACTION_SEND, Uri.fromParts("sms", contacts.get(position).phone.toString(), null));
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
