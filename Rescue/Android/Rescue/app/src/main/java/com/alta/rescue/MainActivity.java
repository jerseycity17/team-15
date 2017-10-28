package com.alta.rescue;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
    DatabaseReference pinglocationlong;
    DatabaseReference pinglocationlat;
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
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.countries_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("Country", getResources().getStringArray(R.array.countries_array)[pos]).commit();
                int selected = currentview;
                currentview = currentview+500;
                navigation.setSelectedItemId(selected);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //lolno
            }

        });
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
        pingtime = database.getReference("Users/" + user.getUid().toString() + "/last_ping");
        pinglocationlat = database.getReference("Users/" + user.getUid().toString() + "/last_location/latitude");
        pinglocationlong = database.getReference("Users/" + user.getUid().toString() + "/last_location/longitude");



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
            }
        });
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
        Log.e("location received", "yay");
        safeRef.setValue(0);
        pinglocationlat.setValue(location.getLatitude());
        pinglocationlong.setValue(location.getLongitude());
        pingtime.setValue(new SimpleDateFormat("yyyyMMdd").format(java.util.Calendar.getInstance().getTime()));
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

