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
 * Created by apatters on 6/17/2017.
 */
public class MockDatabaseService extends DatabaseService{

    public HashMap<String, UserData> userData = new HashMap<>();

//    // personal data of personalData
//    private HashMap<String, PersonalData> userData.personalData = new HashMap<>();
//    // passwords of each personalData
//    private HashMap<String, String> passwordMap = new HashMap<>();
//    // own post of personalData
//    private HashMap<String, ArrayList<Post>> ownPostsMap = new HashMap<>();
//    // messages sent to each user
//    private HashMap<String, ArrayList<Post>> messagesMap = new HashMap<>();
//    // array of personalData following a user
//    private HashMap<String, ArrayList<String>> followedByMap = new HashMap<>();
//    // array of personalData that a user follows
//    private HashMap<String, ArrayList<String>> followingMap = new HashMap<>();

    private static MockDatabaseService instance = null;


    public static MockDatabaseService getInstance(){
        if(instance==null){
            instance = new MockDatabaseService();

        }
        return instance;
    }

    private MockDatabaseService(){}

    private Boolean connected = false;
    public void  connect() {

        if(connected){
            return;
        }

        ArrayList<String> mockUserNames = new ArrayList<>(Arrays.asList("Anne", "Kim", "Dan", "Bob", "Pam", "Jen"));
        for (int idx = 0; idx < mockUserNames.size(); idx++) {

            String mockUserName = mockUserNames.get(idx);

            MockUserData mockUserData = new MockUserData(mockUserName);
            addUser(mockUserName, mockUserData.personalData, mockUserData.password);
        }

        for (int idx = 0; idx < mockUserNames.size(); idx++) {

            String mockUserName = mockUserNames.get(idx);

            try {
                addPost(mockUserName, new Post(mockUserName, "Post 1"));
                addPost(mockUserName, new Post(mockUserName, "Post 2"));

                addMessage(mockUserName, new Post("friend1", "Post 1"));
                addMessage(mockUserName, new Post("friend2", "Post 2"));

                addPremium(mockUserName, new PremiumContent(MockUserData.imageInc()));
                addPremium(mockUserName, new PremiumContent(MockUserData.imageInc()));


                int start = (idx + 1) % mockUserNames.size();
                ArrayList<String> followedBy = new ArrayList<>(2);
                followedBy.add(mockUserNames.get(start++));
                start = (start + 1) % mockUserNames.size();
                followedBy.add(mockUserNames.get(start));

                ArrayList<String> following = new ArrayList<>(2);

                start = (start + 1) % mockUserNames.size();
                following.add(mockUserNames.get(start));
                start = (start + 1) % mockUserNames.size();
                following.add(mockUserNames.get(start));

                following.forEach(followee -> {
                    try {
                        addSubscription(mockUserName, followee);
                    } catch (UserNotExistsException e) {
                        e.printStackTrace();
                    }
                });

            } catch (UserNotExistsException e) {
                e.printStackTrace();
            }
        }

        connected = true;
    }

    @Override
    public void close() {
        userData = null;
    }

    public void addUser(String username, PersonalData personalData, String password){
        if (userData.containsKey(username)) {
            String msg = String.format("user %s alread exists", username);
            throw new Error(msg);
        }

        UserData newUserData = new UserData(username, password, personalData);
        userData.put(username, newUserData);
    }

    public Boolean checkUser(String username){
        return userData.containsKey(username);
    }

    public void assertUserExists(String username) throws UserNotExistsException {
        if(!checkUser(username)){
            throw new UserNotExistsException(username);
        }
    }

    public void deleteUser(String username) throws UserNotExistsException {
        assertUserExists(username);
        userData.remove(username);
        // consider how to update the follow maps of other users that include the deleted user
    }

    /**
     * find the personal data for a single user
     * @param username the user's username
     * @return the personal data
     */
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

    /**
     * find the personal data of multiple personalData
     * @param query the query string to match multiple user names
     * @return map of matching personalData
     */
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

    /**
     * set the personal data of a user
     * @param username - user's username
     * @param personalData - personal data
     */
    public void setPersonalData(String username, PersonalData personalData) throws UserNotExistsException {
        assertUserExists(username);
        userData.get(username).personalData = personalData;
    }


    /**
     * get a user's password
     * @param username
     * @return password
     */
    public String getPassword(String username) throws UserNotExistsException {
        assertUserExists(username);
        return userData.get(username).password;
    }

    /**
     * set the password of a user
     * @param username
     * @param password - updated password
     */
    public void setPassword(String username, String password) throws UserNotExistsException {
        assertUserExists(username);
        userData.get(username).password = password;
    }




    public ArrayList<Post> getOwnPosts(String username) throws UserNotExistsException {
        assertUserExists(username);
        String query = ("^"+username+"$");
        ArrayList<Post> matches =  getOwnPostsMultiple(query);
        // consider checking if user is not found
        return matches;
    }
    // consider removing
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

    public ArrayList<Post> getFollowedPosts(String username) throws UserNotExistsException {
        assertUserExists(username);
        ArrayList<Post> followedPosts = new ArrayList<>();
        userData.get(username).ownPosts.forEach(post->followedPosts.add(post));
        userData.get(username).following.forEach(name->{
            userData.get(name).ownPosts.forEach(post->followedPosts.add(post));
        });

        return followedPosts;
    }

    public void addPost(String username, Post post) throws UserNotExistsException {
        assertUserExists(username);
        userData.get(username).ownPosts.add(post);
    }

    public void deletePost(String username, int idx) throws UserNotExistsException, InvalidDataException {
        assertUserExists(username);
        ArrayList<Post> posts = userData.get(username).ownPosts;
        if(posts.size()>idx){
            posts.remove(idx);
        }else{
            throw new InvalidDataException(String.format("Post of index %d does not exist", idx));
        }
    }

    public ArrayList<Post> getMessages(String username) throws UserNotExistsException {
        assertUserExists(username);
        return userData.get(username).messages;
    }

    public void addMessage(String recipient, Post message) throws UserNotExistsException {
        assertUserExists(recipient);
        userData.get(recipient).messages.add(message);
    }

    public void deleteMessage(String username, int idx) throws UserNotExistsException, InvalidDataException {
        assertUserExists(username);
        ArrayList<Post> messages = userData.get(username).messages;
        if(messages.size()>idx){
            messages.remove(idx);
        }else{
            throw new InvalidDataException(String.format("Message of index %d does not exist", idx));
        }
    }

    public void addFollower(String followeeUsername, String followerUsername) throws UserNotExistsException {
        assertUserExists(followeeUsername);
        assertUserExists(followerUsername);
        ArrayList<String> followedBy = userData.get(followeeUsername).followedBy;
        if(!followedBy.contains(followerUsername)){
            followedBy.add(followerUsername);
            userData.get(followerUsername).following.add(followeeUsername);
        }
    }

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


    protected void addFollowee(String followeeUsername, String followerUsername) throws UserNotExistsException {
        assertUserExists(followeeUsername);
        assertUserExists(followerUsername);
        ArrayList<String> following = userData.get(followerUsername).following;
        if(!following.contains(followeeUsername)){
            following.add(followeeUsername);
        }
    }

    protected void deleteFollowee(String followeeUsername, String followerUsername) throws UserNotExistsException {
        assertUserExists(followeeUsername);
        assertUserExists(followerUsername);
        ArrayList<String> following = userData.get(followerUsername).followedBy;
        int idx = following.indexOf(followeeUsername);
        if(!(idx==-1)){
            following.remove(followeeUsername);
        }
    }


    public ArrayList<PremiumContent> getPremium(String username) throws UserNotExistsException {
        assertUserExists(username);
        return userData.get(username).premiumContent;
    }

    public void addPremium(String username, PremiumContent content) throws UserNotExistsException {
        assertUserExists(username);
        userData.get(username).premiumContent.add(content);
    }

    public void deletePremium(String username, int index) throws UserNotExistsException {
        assertUserExists(username);
        userData.get(username).premiumContent.remove(index);
    }
}


class MockUserData {
    public PersonalData personalData;
    public String password;

    private static int imgCount = 0;

    public static String imageInc(){
        imgCount=(imgCount+1)%20;
        return String.format("static/%d.png",imgCount);
    }

    public MockUserData(String username) {
        personalData = new PersonalData(username, username + "_location", new Date(), username + "_business", imageInc());
        password = username + "pw";
    }
}