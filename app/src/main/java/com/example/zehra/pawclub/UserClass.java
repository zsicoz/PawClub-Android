package com.example.zehra.pawclub;

public class UserClass {
    private int id;
    private String Name_Surname;
    private String UserName;
    private String Email;
    private String Password;
    private String User_Type;
    private int ShelterId;
    private String ProfilePhoto;

    public UserClass(String nameSurname, String username, String email, String password, String userType, String profilePhoto){
        this.Name_Surname = nameSurname;
        this.UserName = username;
        this.Email = email;
        this.Password = password;
        this.User_Type = userType;
        this.ProfilePhoto = profilePhoto;
    }
    public  UserClass(){}

    public String getNameSurname() {
        return Name_Surname;
    }

    public void setNameSurname(String nameSurname) {
        Name_Surname = nameSurname;
    }

    public String getUsername() {
        return UserName;
    }

    public void setUsername(String username) {
        UserName = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserType() {
        return User_Type;
    }

    public void setUserType(String userType) {
        User_Type = userType;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShelter_id() {
        return ShelterId;
    }

    public void setShelter_id(int shelter_id) {
        ShelterId = shelter_id;
    }
}
