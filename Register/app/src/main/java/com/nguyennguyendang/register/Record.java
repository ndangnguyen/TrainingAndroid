package com.nguyennguyendang.register;

import java.util.ArrayList;

public class Record {
    private String name, email, phone, gender, position, location, language;

    static private ArrayList<Record> listRecord = null;

    public Record(){
        if (listRecord == null) listRecord = new ArrayList<>();
    }

    public Record(String name, String email, String phone, String gender, String position, String location, String language) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.position = position;
        this.location = location;
        this.language = language;
    }

    public static ArrayList<Record> getListRecord() {
        return listRecord;
    }

    public static void setListRecord(ArrayList<Record> listRecord) {
        Record.listRecord = listRecord;
    }

    public static void addRecord(Record record) {
        listRecord.add(record);
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        String s = "Name: "+getName() + "\n";
        s += "Email: "+getEmail() + "\n";
        s += "Phone: "+getPhone() + "\n";
        s += "Gender: "+getGender() + "\n";
        s += "Position: "+getPosition() + "\n";
        s += "Location: "+getLocation() + "\n";
        s += "Language: "+getLanguage() + "\n";
        return s;
    }
}
