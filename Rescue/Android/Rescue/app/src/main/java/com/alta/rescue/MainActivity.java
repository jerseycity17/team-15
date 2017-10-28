package com.alta.rescue;

import android.content.Intent;
import android.icu.util.Calendar;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.LocationRequest;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vishalsojitra.easylocation.EasyLocationAppCompatActivity;
import com.vishalsojitra.easylocation.EasyLocationRequest;
import com.vishalsojitra.easylocation.EasyLocationRequestBuilder;

import java.text.SimpleDateFormat;

public class MainActivity extends EasyLocationAppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    BottomNavigationView navigation;
    int currentview;
    TextView alert;
    DatabaseReference pingtime;
    DatabaseReference pinglocation;
    DatabaseReference safeRef;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (currentview != item.getItemId()) {
                currentview = item.getItemId();
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        BriefFragment brieffragment = new BriefFragment();
                        transaction.replace(R.id.fragment_container, brieffragment);
                        transaction.commit();
                        return true;
                    case R.id.navigation_dashboard:
                        ContactFragment contactfragment = new ContactFragment();
                        transaction.replace(R.id.fragment_container, contactfragment);
                        transaction.commit();
                        return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(ContextCompat.getColor(this,android.R.color.white));
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        alert=(TextView)findViewById(R.id.alert);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_home);
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        safeRef = database.getReference("Users/" + user.getUid().toString() + "/safetyCheck");
        pinglocation = database.getReference("Users/" + user.getUid().toString() + "/last_ping");
        pingtime = database.getReference("Users/" + user.getUid().toString() + "/last_location");


        safeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int needsCheck = Integer.valueOf(dataSnapshot.getValue().toString());
                if (needsCheck==1){
                    alert.setVisibility(View.VISIBLE);
                }else{
                    alert.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        //userRef.child();
        //FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
 //       userRef.setValue(new Agent("David","Margolin",3472338802L,new LatLng(25.345345,23.23423),4489484L));
//        DatabaseReference locationRef = database.getReference("Regions/location_id_here");
//        ArrayList<Briefing> brief = new ArrayList<Briefing>();
//        brief.add(new Briefing("brief1_title","brief1_text", 51651516L,5));
//        ArrayList<String> agents = new ArrayList<String>();
//        agents.add("Userid1");
//        agents.add("Userid3");
//        locationRef.setValue(new Region("Russia",brief,agents));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                startActivity(i);
                return true;
            case R.id.ping:
                LocationRequest locationRequest = new LocationRequest()
                        .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                        .setInterval(5000)
                        .setFastestInterval(5000);
                EasyLocationRequest easyLocationRequest = new EasyLocationRequestBuilder()
                        .setLocationRequest(locationRequest)
                        .setFallBackToLastLocationTime(3000)
                        .build();
                requestSingleLocationFix(easyLocationRequest);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        navigation.setSelectedItemId(savedInstanceState.getInt("view"));
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("view", currentview);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onLocationPermissionGranted() {
        Log.e("location perm granted", "yay");

    }

    @Override
    public void onLocationPermissionDenied() {
        Log.e("location perm failed", "boo");

    }

    @Override
    public void onLocationReceived(Location location) {
        safeRef.setValue(0);
        pinglocation.setValue(location);
        pingtime.setValue(new SimpleDateFormat("YYYYMMDD").format(java.util.Calendar.getInstance().getTime()));
    }

    @Override
    public void onLocationProviderEnabled() {
        Log.e("location prov enabled", "yay");
    }

    @Override
    public void onLocationProviderDisabled() {
        Log.e("location prov disabled", "boo");

    }

}

