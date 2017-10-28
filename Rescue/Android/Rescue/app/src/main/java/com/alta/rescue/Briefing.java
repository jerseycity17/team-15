package com.alta.rescue;


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
