package com.alta.rescue;

import java.util.ArrayList;

/**
 * Created by David Margolin on 10/27/2017.
 */

public class Region {
    public Region(String name, ArrayList<Briefing> briefs, ArrayList<String> users) {
        this.name = name;
        this.briefs = briefs;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Briefing> getBriefs() {
        return briefs;
    }

    public void setBriefs(ArrayList<Briefing> briefs) {
        this.briefs = briefs;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    private String name;
    private ArrayList<Briefing> briefs;
    private ArrayList<String> users;
    private ArrayList<Contact> contacts;
}
