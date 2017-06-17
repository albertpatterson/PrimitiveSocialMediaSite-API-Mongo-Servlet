package com.primitive_social_media.database;

import com.primitive_social_media.PersonalData;
import com.primitive_social_media.Post;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by apatters on 6/17/2017.
 */
public abstract class DatabaseService {


    public abstract void connect();
    public abstract void close();


    public abstract void addUser(String username, PersonalData personalData, String password);

    public abstract Boolean checkUser(String username);

    public abstract void deleteUser(String username);

    /**
     * find the personal data for a single user
     * @param username the user's username
     * @return the personal data
     */
    public abstract PersonalData findPersonalData(String username);

    /**
     * find the personal data of multiple personalData
     * @param query the query string to match multiple user names
     * @return map of matching personalData
     */
    public abstract  HashMap<String, PersonalData> findPersonalDataMultiple(String query);

    /**
     * set the personal data of a user
     * @param username - user's username
     * @param personalData - personal data
     */
    public abstract void setPersonalData(String username, PersonalData personalData);


    /**
     * get a user's password
     * @param username
     * @return password
     */
    public abstract String getPassword(String username);

    /**
     * set the password of a user
     * @param username
     * @param password - updated password
     */
    public abstract void setPassword(String username, String password);




    public abstract ArrayList<Post> getOwnPosts(String username);


    public abstract ArrayList<Post> getFollowedPosts(String username);

    public abstract void addPost(String username, Post post);

    public abstract void deletePost(String username, int idx);





    public abstract ArrayList<Post> getMessages(String username);

    public abstract void addMessage(String username, Post message);

    public abstract void deleteMessage(String username, int idx);

    public abstract ArrayList<String> getFollowedBy(String username);

    public abstract void addFollower(String followeeUsername, String followerUsername);

    public abstract void deleteFollower(String followeeUsername, String followerUsername);


    protected abstract void addFollowee(String followeeUsername, String followerUsername);

    protected abstract void deleteFollowee(String followeeUsername, String followerUsername);
}
