package com.nguyennguyendang.contacts;

import java.util.ArrayList;

public class Contact {

    public Contact() {
        if (list == null) list = new ArrayList<>();
    }
    private static ArrayList<Person> list;

    public static ArrayList<Person> getList() {
        return list;
    }

    public static void setList(ArrayList<Person> list) {
        Contact.list = list;
    }

    public static void add(Person p) {
        list.add(p);
    }

    public static void del(Person p) {
        list.remove(p);
    }
}
