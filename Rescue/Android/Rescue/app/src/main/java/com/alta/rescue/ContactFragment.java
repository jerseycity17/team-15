package com.alta.rescue;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ContactFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    RecyclerView recyclerView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactFragment() {
    }


    public static ContactFragment newInstance(int columnCount) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Regions/location_id_here/contacts");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<Contact> contacts = new ArrayList<Contact>();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Contact contact = messageSnapshot.getValue(Contact.class);
                    contacts.add(contact);
                }
                recyclerView.setAdapter(new ContactRecyclerViewAdapter(contacts, getActivity(), mListener));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
//        void onListFragmentInteraction(DummyItem item);
    }
}
