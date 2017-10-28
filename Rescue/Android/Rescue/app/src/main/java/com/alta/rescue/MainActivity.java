package com.alta.rescue;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yayandroid.locationmanager.LocationManager;

public class MainActivity extends AppCompatActivity{

    private FirebaseAnalytics mFirebaseAnalytics;
    BottomNavigationView navigation;
    int currentview;
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
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_home);
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Users/" + user.getUid().toString());
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
}

