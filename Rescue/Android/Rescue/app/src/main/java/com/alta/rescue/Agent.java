package com.alta.rescue;
import android.support.v4.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by David Margolin on 10/27/2017.
 */

public class Agent {
    public Agent(String first, String last, Long phone, String country_id, LatLng last_location, long last_ping) {
        this.first = first;
        this.last = last;
        this.phone = phone;
        this.country_id = country_id;
        this.last_location = last_location;
        this.last_ping = last_ping;
    }
    String first;
    String last;
    Long phone;
    String country_id;
    LatLng last_location;
    long last_ping;
}
