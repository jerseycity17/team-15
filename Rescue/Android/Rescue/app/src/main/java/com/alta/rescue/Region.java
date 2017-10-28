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

    String name;
    ArrayList<Briefing> briefs;
    ArrayList<String> users;
    ArrayList<Contact> contacts;
}
