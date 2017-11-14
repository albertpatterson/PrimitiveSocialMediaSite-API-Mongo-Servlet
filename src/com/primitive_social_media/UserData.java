package com.primitive_social_media;

import java.util.ArrayList;

/**
 * @desc Representation of user data stored in the database
 */
public class UserData {

    public String username;
    public String password;
    public PersonalData  personalData;
    public ArrayList<Post> ownPosts = new ArrayList<>();
    public ArrayList<Post> messages = new ArrayList<>();
    public ArrayList<String> followedBy = new ArrayList<>();
    public ArrayList<String> following = new ArrayList<>();
    public ArrayList<PremiumContent> premiumContent = new ArrayList<>();

    /**
     * create a new instance of user data
     * @param username the name of the user
     * @param password the user's password
     * @param personalData the user's personal data
     * @param personalData the user's personal data
     */
    public UserData(String username, String password, PersonalData personalData){
        this.username = username;
        this.password = password;
        this.personalData = personalData;
    }

}
