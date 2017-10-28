package com.alta.rescue;

/**
 * Created by David Margolin on 10/28/2017.
 */

public class Contact {
    public Contact(String name, String occupation, String phone) {
        this.name = name;
        this.occupation = occupation;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String name;
    private String occupation;
    private String phone;
}
