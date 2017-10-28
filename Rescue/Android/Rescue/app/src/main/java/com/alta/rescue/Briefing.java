package com.alta.rescue;

import java.util.Date;

/**
 * Created by David Margolin on 10/27/2017.
 */

public class Briefing {
    public Briefing(){

    }
    public Briefing(String title, String text, Long date, Integer urgency) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.urgency = urgency;
    }
    String title;
    String text;
    Long date;
    Integer urgency;
}
