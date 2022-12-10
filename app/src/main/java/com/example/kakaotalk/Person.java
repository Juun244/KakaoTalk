package com.example.kakaotalk;

public class Person {
    String name;
    String text;

    public Person(String name, String text) {
        this.name = name;
        this.text = text;
    }


    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
