package com.example.ontheway;

public class model {

    String name,email,district,city,skill,contact,latitudeTextView,longitTextView;

    public model(String name, String email, String district, String city, String skill, String contact,String latitudeTextView,String longitTextView) {
        this.name = name;
        this.email = email;
        this.district = district;
        this.city = city;
        this.skill = skill;
        this.contact = contact;
        this.latitudeTextView =latitudeTextView;
        this.longitTextView =longitTextView;

    }

    public model() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLatitudeTextView() {
        return latitudeTextView;
    }

    public void setLatitudeTextView(String latitudeTextView) {
        this.latitudeTextView = latitudeTextView;
    }

    public String getLongitTextView() {
        return longitTextView;
    }

    public void setLongitTextView(String longitTextView) {
        this.longitTextView = longitTextView;
    }
}
