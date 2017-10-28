package com.alta.rescue;

import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Team Atlas]] on 10/27/2017.
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

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getSafetyCheck() {
        return safetyCheck;
    }

    public void setSafetyCheck(Boolean safetyCheck) {
        this.safetyCheck = safetyCheck;
    }

    public LatLng getLast_location() {
        return last_location;
    }

    public void setLast_location(LatLng last_location) {
        this.last_location = last_location;
    }

    public long getLast_ping() {
        return last_ping;
    }

    public void setLast_ping(long last_ping) {
        this.last_ping = last_ping;
    }

    private String first;
    private String last;
    private String phone;
    private Boolean safetyCheck;
    private LatLng last_location;
    private long last_ping;
}
