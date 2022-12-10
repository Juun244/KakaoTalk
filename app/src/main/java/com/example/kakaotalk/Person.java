package com.example.kakaotalk;

public class Person {
    String name;
    String text;
    String date;

    public Person(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public Person(String name, String text, String date) {
        this.name = name;
        this.text = text;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
    
    public String getDateText() {
        return date;
    }

    public void setText(String text) {
        this.text = text;
    }
}
