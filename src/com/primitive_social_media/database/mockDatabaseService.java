package com.primitive_social_media.database;

import com.primitive_social_media.PersonalData;
import com.primitive_social_media.Post;
import com.primitive_social_media.UserData;
import com.primitive_social_media.exception.InvalidDataException;
import com.primitive_social_media.exception.UserNotExistsException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by apatters on 6/17/2017.
 */
public class MockDatabaseService extends DatabaseService{

    public HashMap<String, UserData> userData = new HashMap<>();

//    // personal data of personalData
//    private HashMap<String, PersonalData> userData.personalData = new HashMap<>();
//    // passwords of each personalData
//    private HashMap<String, String> passwordMap = new HashMap<>();
//    // own posts of personalData
//    private HashMap<String, ArrayList<Post>> ownPostsMap = new HashMap<>();
//    // messages sent to each user
//    private HashMap<String, ArrayList<Post>> messagesMap = new HashMap<>();
//    // array of personalData following a user
//    private HashMap<String, ArrayList<String>> followedByMap = new HashMap<>();
//    // array of personalData that a user follows
//    private HashMap<String, ArrayList<String>> followingMap = new HashMap<>();

    public void  connect(){
        try{
            String username;
            username = "Bob";
            addUser(username,
                    new PersonalData(username, username + "'s town", "01/02/2000", username+"'s business", username+"'s picture"),
                     username+"'s password");
                addPost(username, new Post(username, username + "'s post 1"));
                addPost(username, new Post(username, username + "'s post 2"));

            username = "Kim";
            addUser(username,
                    new PersonalData(username, username + "'s town", "01/02/2000", username+"'s business", username+"'s picture"),
                    username+"'s password");
            addPost(username, new Post(username, username+"'s post 1"));
            addPost(username, new Post(username, username+"'s post 2"));

            username = "Raj";
            addUser(username,
                    new PersonalData(username, username + "'s town", "01/02/2000", username+"'s business", username+"'s picture"),
                    username+"'s password");
            addPost(username, new Post(username, username+"'s post 1"));
            addPost(username, new Post(username, username+"'s post 2"));

            addFollower("Bob", "Raj");
            addFollower("Kim", "Raj");

            addMessage("Kim", new Post("Raj", "Hello Kim"));
        }catch(UserNotExistsException e){
            System.out.println(e);
        }
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
        HashMap<String, PersonalData> matches = new HashMap<>();
        for(HashMap.Entry<String, UserData> entry: userData.entrySet()){
            String key = entry.getKey();
            if(key.matches(query)){
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

    public void addMessage(String username, Post message) throws UserNotExistsException {
        assertUserExists(username);
        userData.get(username).messages.add(message);
    }

    public void deleteMessage(String username, int idx) throws UserNotExistsException, InvalidDataException {
        assertUserExists(username);
        userData.get(username).messages.remove(idx);
        ArrayList<Post> messages = userData.get(username).messages;
        if(messages.size()>idx){
            messages.remove(idx);
        }else{
            throw new InvalidDataException(String.format("Message of index %d does not exist", idx));
        }
    }


//    public ArrayList<String> getFollowedBy(String username){
//        return userData.get(username).followedBy;
//    }

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


}
