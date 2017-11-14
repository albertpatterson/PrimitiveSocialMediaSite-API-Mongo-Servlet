package com.primitive_social_media.database;

import com.primitive_social_media.PersonalData;
import com.primitive_social_media.Post;
import com.primitive_social_media.PremiumContent;
import com.primitive_social_media.exception.InvalidDataException;
import com.primitive_social_media.exception.UserNotExistsException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *  @desc Service for querying and updating data stored in a database
 */
public abstract class DatabaseService {

    /**
     * connect to the database
     */
    public abstract void connect();

    /**
     * close the connection to the database
     */
    public abstract void close();

    /**
     * add a new user
     * @param username - new user's username
     * @param personalData - new user's personal Data
     * @param password - new user's password
     */
    public abstract void addUser(String username, PersonalData personalData, String password);

    /**
     * check if a user exists
     * @param username - the username to check
     * @return boolean indicating if the user exists
     */
    public abstract Boolean checkUser(String username);

    /**
     * delete a user
     * @param username - the name of the user to remove
     * @throws UserNotExistsException
     */
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

    /**
     * get the posts created by a user
     * @param username -  the name of the user whose posts are required
     * @return - collection of posts
     * @throws UserNotExistsException
     */
    public abstract ArrayList<Post> getOwnPosts(String username) throws UserNotExistsException;

    /**
     * get all posts created by followed users
     * @param username - the name of the user whose followed posts are required
     * @return collection of posts
     * @throws UserNotExistsException
     */
    public abstract ArrayList<Post> getFollowedPosts(String username) throws UserNotExistsException;

    /**
     * add a new post
     * @param username - the poster's username
     * @param post - the post to add
     * @throws UserNotExistsException
     */
    public abstract void addPost(String username, Post post) throws UserNotExistsException;

    /**
     * delete a post
     * @param username - the name of the poster
     * @param idx - the index of the post to delete
     * @throws UserNotExistsException
     * @throws InvalidDataException
     */
    public abstract void deletePost(String username, int idx) throws UserNotExistsException, InvalidDataException;

    /**
     * get a user's messages
     * @param username - the name of the user whose messages are required
     * @return collection of messages
     * @throws UserNotExistsException
     */
    public abstract ArrayList<Post> getMessages(String username) throws UserNotExistsException;

    /**
     * add a new message
     * @param username - name of the message sender
     * @param message - the message to send
     * @throws UserNotExistsException
     */
    public abstract void addMessage(String username, Post message) throws UserNotExistsException;

    /**
     * delete a message
     * @param username - the name of the message recipient
     * @param idx - the index of the message to delete
     * @throws UserNotExistsException
     * @throws InvalidDataException
     */
    public abstract void deleteMessage(String username, int idx) throws UserNotExistsException, InvalidDataException;

    /**
     * add a follower
     * @param followeeUsername - the name of the user being followed
     * @param followerUsername - the name of the user following the followee
     * @throws UserNotExistsException
     */
    public abstract void addFollower(String followeeUsername, String followerUsername) throws UserNotExistsException;

    /**
     * delete a follower
     * @param followeeUsername - the name of the user being followed
     * @param followerUsername - the name of the user following the followee
     * @throws UserNotExistsException
     */
    public abstract void deleteFollower(String followeeUsername, String followerUsername) throws UserNotExistsException;

    /**
     * get the users following a user
     * @param username the name of the users whose followers are required
     * @return collection of following users
     * @throws UserNotExistsException
     */
    public abstract ArrayList<String> getSubscriptions(String username) throws UserNotExistsException;

    /**
     * add a new subscription
     * @param username the name of the user following the followee
     * @param followee the name of the user being followed
     * @throws UserNotExistsException
     */
    public abstract void addSubscription(String username, String followee) throws UserNotExistsException;

    /**
     * delete an existing subscription
     * @param username the name of the subscribed user
     * @param followee the name of the user being followed
     * @throws UserNotExistsException
     */
    public abstract void deleteSubscription(String username, String followee) throws UserNotExistsException;

    /**
     * add a new followee
     * @param followeeUsername - the name of the user being followed
     * @param followerUsername - the name of the user following the followee
     * @throws UserNotExistsException
     */
    protected abstract void addFollowee(String followeeUsername, String followerUsername) throws UserNotExistsException;

    /**
     * delete an existing followee
     * @param followeeUsername - the name of the user being followed
     * @param followerUsername - the name of the user following the followee
     * @throws UserNotExistsException
     */
    protected abstract void deleteFollowee(String followeeUsername, String followerUsername) throws UserNotExistsException;

    /**
     * get the premium content submitted by the user
     * @param username - the name of the user whose premium content is required
     * @return collection of submitted premium content
     * @throws UserNotExistsException
     */
    public abstract ArrayList<PremiumContent> getPremium(String username) throws UserNotExistsException;

    /**
     * add a new premium item
     * @param username - the name of the user adding the item
     * @param content - the content to add
     * @throws UserNotExistsException
     */
    public abstract void addPremium(String username, PremiumContent content) throws UserNotExistsException;

    /**
     * delete a premium item
     * @param username - the name of the user owning the content
     * @param index -  the index of the item to remove
     * @throws UserNotExistsException
     */
    public abstract void deletePremium(String username, int index) throws UserNotExistsException;
}
