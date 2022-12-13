package com.example.kakaotalk;

public class Person {
    private String email, password;
    private String name, birth, tellPhone, text;
    private String friendList;

    public Person() {}

    public Person(String email, String name, String text) {
        this.email = email;
        this.name = name;
        this.text = text;
    }

    public Person(String email, String password, String name, String tellPhone, String birth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tellPhone = tellPhone;
        this.birth = birth;
    }

    public Person(String email, String password, String name, String tellPhone, String birth, String text) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tellPhone = tellPhone;
        this.birth = birth;
        this.text = text;
    }

    public String getName() {
        return name;
    }
    public String getBirth() {
        return birth;
    }
    public String getFriendList() {
        return friendList;
    }
    public String getTellPhone() {
        return tellPhone;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {return password;}


    public void setName(String name) {
        this.name = name;
    }
    public void setBirth(String birth) {
        this.birth = birth;
    }
    public void setTellPhone(String tellPhone) {
        this.tellPhone = tellPhone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFriendList(String friendList) {
        this.friendList = friendList;
    }
    public void setPassword(String password) {this.password = password;}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
