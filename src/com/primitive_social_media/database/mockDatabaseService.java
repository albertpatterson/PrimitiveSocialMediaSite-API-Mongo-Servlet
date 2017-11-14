package com.primitive_social_media.database;

import com.primitive_social_media.PersonalData;
import com.primitive_social_media.Post;
import com.primitive_social_media.PremiumContent;
import com.primitive_social_media.UserData;
import com.primitive_social_media.exception.InvalidDataException;
import com.primitive_social_media.exception.UserNotExistsException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @desk a mock database service storing data in memory
 */
public class MockDatabaseService extends DatabaseService{

    public  static HashMap<String, UserData> userData = new HashMap<>();

    private static MockDatabaseService instance = null;

    private static Boolean connected = false;

    /**
     * get the mock database instance
     * @return the instance of the mock database
     */
    public static MockDatabaseService getInstance(){
        if(instance==null){
            instance = new MockDatabaseService();

        }
        return instance;
    }

    private MockDatabaseService(){}

    /**
     * initialize the data stored in memory, emulating a connection to the database
     */
    @Override
    public void connect() {

        if(connected){
            return;
        }

        // create several mock users
        ArrayList<String> mockUserNames = new ArrayList<>(Arrays.asList("Anne", "Kim", "Dan", "Bob", "Pam", "Jen"));
        for (int idx = 0; idx < mockUserNames.size(); idx++) {

            String mockUserName = mockUserNames.get(idx);

            MockUserData mockUserData = new MockUserData(mockUserName);
            addUser(mockUserName, mockUserData.personalData, mockUserData.password);
        }

        // update each user with posts, premium content, followers, and messages
        for (int idx = 0; idx < mockUserNames.size(); idx++) {

            String mockUserName = mockUserNames.get(idx);

            try {

                // add activity by user
                // add post by user
                addPost(mockUserName, new Post(mockUserName, "Post 1"));
                addPost(mockUserName, new Post(mockUserName, "Post 2"));
                // add premium by user
                addPremium(mockUserName, new PremiumContent(MockUserData.imageInc()));
                addPremium(mockUserName, new PremiumContent(MockUserData.imageInc()));

                // add subscriptions and messages
                int friendIdx;
                int nFriends = 2;
                String friendName;
                for(int friendOffset = 1; friendOffset<=nFriends; friendOffset++){
                    friendIdx = (idx + friendOffset) % mockUserNames.size();
                    friendName = mockUserNames.get(friendIdx);
                    addMessage(mockUserName, new Post(friendName, "Post from friend"));
                    addSubscription(mockUserName, friendName);
                }
            } catch (UserNotExistsException e) {
                e.printStackTrace();
            }
        }

        connected = true;
    }

    @Override
    public void close() {}

    @Override
    public void addUser(String username, PersonalData personalData, String password){
        if (userData.containsKey(username)) {
            String msg = String.format("user %s alread exists", username);
            throw new Error(msg);
        }

        UserData newUserData = new UserData(username, password, personalData);
        userData.put(username, newUserData);
    }

    @Override
    public Boolean checkUser(String username){
        return userData.containsKey(username);
    }

    /**
     * assert that a user exists in the database
     * @param username - the name of the user exected to exist
     * @throws UserNotExistsException
     */
    public void assertUserExists(String username) throws UserNotExistsException {
        if(!checkUser(username)){
            throw new UserNotExistsException(username);
        }
    }

    @Override
    public void deleteUser(String username) throws UserNotExistsException {
        assertUserExists(username);
        userData.remove(username);
        // consider how to update the follow maps of other users that include the deleted user
    }

    @Override
    public PersonalData findPersonalData(String username) throws UserNotExistsException {
        assertUserExists(username);
        String query = ("^"+username+"$");
        HashMap<String, PersonalData> matches =  findPersonalDataMultiple(query);
        if(!(matches.size()==1)){
            String msg = String.format("User %s not found.", username);
            throw new Error();
        }
        return matches.get(username);
    }

    @Override
    public HashMap<String, PersonalData> findPersonalDataMultiple(String query){

        Pattern p = Pattern.compile(query);
        HashMap<String, PersonalData> matches = new HashMap<>();
        for(HashMap.Entry<String, UserData> entry: userData.entrySet()){
            String key = entry.getKey();
            Matcher m = p.matcher(key);
            if(m.matches()){
                matches.put(key, new PersonalData(userData.get(key).personalData));
            }
        }
    return matches;
    }

    @Override
    public void setPersonalData(String username, PersonalData personalData) throws UserNotExistsException {
        assertUserExists(username);
        userData.get(username).personalData = personalData;
    }

    @Override
    public String getPassword(String username) throws UserNotExistsException {
        assertUserExists(username);
        return userData.get(username).password;
    }

    @Override
    public void setPassword(String username, String password) throws UserNotExistsException {
        assertUserExists(username);
        userData.get(username).password = password;
    }

    @Override
    public ArrayList<Post> getOwnPosts(String username) throws UserNotExistsException {
        assertUserExists(username);
        String query = ("^"+username+"$");
        ArrayList<Post> matches =  getOwnPostsMultiple(query);
        // consider checking if user is not found
        return matches;
    }

    /**
     * get the posts created by several users
     * @param query - query to match several users
     * @return - collection of posts created by matching users
     */
    public ArrayList<Post> getOwnPostsMultiple(String query){
        ArrayList<Post> matches = new ArrayList<>();
        for(HashMap.Entry<String, UserData> entry: userData.entrySet()){
            String username = entry.getKey();
            if(username.matches(query)){
                ArrayList<Post> ownPosts = userData.get(username).ownPosts;
                ownPosts.forEach(post->matches.add(new Post(post)));
            }
        }
        return matches;
    }

    @Override
    public ArrayList<Post> getFollowedPosts(String username) throws UserNotExistsException {
        assertUserExists(username);
        ArrayList<Post> followedPosts = new ArrayList<>();
        userData.get(username).ownPosts.forEach(post->followedPosts.add(post));
        userData.get(username).following.forEach(name->{
            userData.get(name).ownPosts.forEach(post->followedPosts.add(post));
        });

        return followedPosts;
    }

    @Override
    public void addPost(String username, Post post) throws UserNotExistsException {
        assertUserExists(username);
        userData.get(username).ownPosts.add(post);
    }

    @Override
    public void deletePost(String username, int idx) throws UserNotExistsException, InvalidDataException {
        assertUserExists(username);
        ArrayList<Post> posts = userData.get(username).ownPosts;
        if(posts.size()>idx){
            posts.remove(idx);
        }else{
            throw new InvalidDataException(String.format("Post of index %d does not exist", idx));
        }
    }

    @Override
    public ArrayList<Post> getMessages(String username) throws UserNotExistsException {
        assertUserExists(username);
        return userData.get(username).messages;
    }

    @Override
    public void addMessage(String recipient, Post message) throws UserNotExistsException {
        assertUserExists(recipient);
        userData.get(recipient).messages.add(message);
    }

    @Override
    public void deleteMessage(String username, int idx) throws UserNotExistsException, InvalidDataException {
        assertUserExists(username);
        ArrayList<Post> messages = userData.get(username).messages;
        if(messages.size()>idx){
            messages.remove(idx);
        }else{
            throw new InvalidDataException(String.format("Message of index %d does not exist", idx));
        }
    }

    @Override
    public void addFollower(String followeeUsername, String followerUsername) throws UserNotExistsException {
        assertUserExists(followeeUsername);
        assertUserExists(followerUsername);
        ArrayList<String> followedBy = userData.get(followeeUsername).followedBy;
        if(!followedBy.contains(followerUsername)){
            followedBy.add(followerUsername);
            userData.get(followerUsername).following.add(followeeUsername);
        }
    }

    @Override
    public void deleteFollower(String followeeUsername, String followerUsername) throws UserNotExistsException {
        assertUserExists(followeeUsername);
        assertUserExists(followerUsername);
        ArrayList<String> followedBy = userData.get(followeeUsername).followedBy;
        int idx = followedBy.indexOf(followerUsername);
        if(!(idx==-1)){
            followedBy.remove(followerUsername);
            userData.get(followerUsername).following.remove(followeeUsername);
        }
    }

    @Override
    public ArrayList<String> getSubscriptions(String username) throws UserNotExistsException {
        assertUserExists(username);
        return userData.get(username).following;
    }

    @Override
    public void addSubscription(String username, String followee) throws UserNotExistsException {
        assertUserExists(username);
        if(getFolloweeIndex(username, followee)==-1){
            userData.get(username).following.add(followee);
        }
    }

    @Override
    public void deleteSubscription(String username, String followee) throws UserNotExistsException {
        assertUserExists(username);
        int followeeIdx = getFolloweeIndex(username, followee);
        if(followeeIdx!=-1){
            userData.get(username).following.remove(followeeIdx);
        }
    }

    private int getFolloweeIndex(String username, String followee) throws UserNotExistsException {
        assertUserExists(username);
        return userData.get(username).following.indexOf(followee);
    }

    @Override
    protected void addFollowee(String followeeUsername, String followerUsername) throws UserNotExistsException {
        assertUserExists(followeeUsername);
        assertUserExists(followerUsername);
        ArrayList<String> following = userData.get(followerUsername).following;
        if(!following.contains(followeeUsername)){
            following.add(followeeUsername);
        }
    }

    @Override
    protected void deleteFollowee(String followeeUsername, String followerUsername) throws UserNotExistsException {
        assertUserExists(followeeUsername);
        assertUserExists(followerUsername);
        ArrayList<String> following = userData.get(followerUsername).followedBy;
        int idx = following.indexOf(followeeUsername);
        if(!(idx==-1)){
            following.remove(followeeUsername);
        }
    }

    @Override
    public ArrayList<PremiumContent> getPremium(String username) throws UserNotExistsException {
        assertUserExists(username);
        return userData.get(username).premiumContent;
    }

    @Override
    public void addPremium(String username, PremiumContent content) throws UserNotExistsException {
        assertUserExists(username);
        userData.get(username).premiumContent.add(content);
    }

    @Override
    public void deletePremium(String username, int index) throws UserNotExistsException {
        assertUserExists(username);
        userData.get(username).premiumContent.remove(index);
    }
}

/**
 * @desc mock data of a user
 */
class MockUserData {
    public PersonalData personalData;
    public String password;

    private static int imgCount = 0;

    /**
     * increment a counter to cycle through images, so that various images are used throughout the app
     * @return
     */
    public static String imageInc(){
        imgCount=(imgCount+1)%20;
        return String.format("static/%d.png",imgCount);
    }

    /**
     * create mock data for a user
     * @param username - the name of the user
     */
    public MockUserData(String username) {
        personalData = new PersonalData(username, username + "_location", new Date(), username + "_business", imageInc());
        password = username + "pw";
    }
}