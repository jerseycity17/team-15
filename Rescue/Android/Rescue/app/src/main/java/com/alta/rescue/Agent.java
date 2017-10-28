package com.alta.rescue;

import com.google.android.gms.maps.model.LatLng;


/**
 * Created by David Margolin on 10/27/2017.
 */

public class Agent {
    public Agent(String first, String last, String phone){
        this.first = first;
        this.last = last;
        this.phone = phone;
    }
    public Agent(String first, String last, String phone, LatLng last_location, long last_ping) {
        this.first = first;
        this.last = last;
        this.phone = phone;
        this.last_location = last_location;
        this.last_ping = last_ping;
    }
    String first;
    String last;
    String phone;
    Boolean safetyCheck;
    LatLng last_location;
    long last_ping;
}
