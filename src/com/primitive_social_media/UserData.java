package com.primitive_social_media;

import java.util.ArrayList;

/**
 * Created by apatters on 6/19/2017.
 */
public class UserData {

    public String username;
    public String password;
    public PersonalData  personalData;
    public ArrayList<Post> ownPosts = new ArrayList<>();
    public ArrayList<Post> messages = new ArrayList<>();
    public ArrayList<String> followedBy = new ArrayList<>();
    public ArrayList<String> following = new ArrayList<>();

    public UserData(String username, String password, PersonalData personalData){
        this.username = username;
        this.password = password;
        this.personalData = personalData;
    }

}
