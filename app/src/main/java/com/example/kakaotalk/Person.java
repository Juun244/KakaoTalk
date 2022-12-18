package com.example.kakaotalk;

<<<<<<< Updated upstream
public class Person {
=======
import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

>>>>>>> Stashed changes
    private String email, password;
    private String name, birth, tellPhone, text;
    private String friendlist, image, backImage;
    private String noImage = null;

    public Person() {}

    public Person(String email, String password, String name, String tellPhone, String birth, String text, String friendlist) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tellPhone = tellPhone;
        this.birth = birth;
        this.text = text;
        this.friendlist = friendlist;
    }


    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String getBirth() {
        return birth;
    }
    public String getFriendList() {
        return friendlist;
    }
    public String getTellPhone() {
        return tellPhone;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {return password;}
    public String getImage() {
        return image;
    }
    public String getBackImage() {
        return backImage;
    }

    public String getId() { return email.substring(0, email.indexOf("@")); }

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
        this.friendlist = friendList;
    }
    public void setPassword(String password) {this.password = password;}
    public void setImage(String image) {
        this.image = image;
    }
    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getText() {
        return text;
    }

    //라디오버튼 위한 select 관리 메서드
    boolean isSelected;
    public boolean getSelected() {
        return isSelected;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setText(String text) {
        this.text = text;
    }
}
