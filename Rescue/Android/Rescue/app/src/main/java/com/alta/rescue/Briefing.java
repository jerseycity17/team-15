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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
    }

    private String title;
    private String text;
    private Long date;
    private Integer urgency;
}
