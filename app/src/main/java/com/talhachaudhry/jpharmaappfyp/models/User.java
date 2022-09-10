package com.talhachaudhry.jpharmaappfyp.models;

public class User {
    String profilePic, name, password, userId, mail;

    public User(String profilePic, String name, String password, String lastMsg, String userId, String mail) {
        this.profilePic = profilePic;
        this.name = name;
        this.password = password;
        this.userId = userId;
        this.mail = mail;
    }

    public User() {
        // Empty constructor
    }

    public User(String name, String password, String mail) {
        this.name = name;
        this.password = password;
        this.mail = mail;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
