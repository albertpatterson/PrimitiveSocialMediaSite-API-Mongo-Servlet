package com.primitive_social_media.database;

import com.primitive_social_media.PersonalData;
import com.primitive_social_media.Post;
import com.primitive_social_media.PremiumContent;
import com.primitive_social_media.exception.InvalidDataException;
import com.primitive_social_media.exception.UserNotExistsException;

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

    public abstract void deleteUser(String username) throws UserNotExistsException;

    /**
     * find the personal data for a single user
     * @param username the user's username
     * @return the personal data
     */
    public abstract PersonalData findPersonalData(String username) throws UserNotExistsException;

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
    public abstract void setPersonalData(String username, PersonalData personalData) throws UserNotExistsException;


    /**
     * get a user's password
     * @param username
     * @return password
     */
    public abstract String getPassword(String username) throws UserNotExistsException;

    /**
     * set the password of a user
     * @param username
     * @param password - updated password
     */
    public abstract void setPassword(String username, String password) throws UserNotExistsException;




    public abstract ArrayList<Post> getOwnPosts(String username) throws UserNotExistsException;


    public abstract ArrayList<Post> getFollowedPosts(String username) throws UserNotExistsException;

    public abstract void addPost(String username, Post post) throws UserNotExistsException;

    public abstract void deletePost(String username, int idx) throws UserNotExistsException, InvalidDataException;





    public abstract ArrayList<Post> getMessages(String username) throws UserNotExistsException;

    public abstract void addMessage(String username, Post message) throws UserNotExistsException;

    public abstract void deleteMessage(String username, int idx) throws UserNotExistsException, InvalidDataException;

//    public abstract ArrayList<String> getFollowedBy(String username);

    public abstract void addFollower(String followeeUsername, String followerUsername) throws UserNotExistsException;

    public abstract void deleteFollower(String followeeUsername, String followerUsername) throws UserNotExistsException;

    public abstract ArrayList<String> getSubscriptions(String username) throws UserNotExistsException;

    public abstract void addSubscription(String username, String followee) throws UserNotExistsException;

    public abstract void deleteSubscription(String username, String followee) throws UserNotExistsException;

    protected abstract void addFollowee(String followeeUsername, String followerUsername) throws UserNotExistsException;

    protected abstract void deleteFollowee(String followeeUsername, String followerUsername) throws UserNotExistsException;

    public abstract ArrayList<PremiumContent> getPremium(String username) throws UserNotExistsException;

    public abstract void addPremium(String username, PremiumContent content) throws UserNotExistsException;

    public abstract void deletePremium(String username, int index) throws UserNotExistsException;
}
