package com.primitive_social_media.database;

import com.primitive_social_media.PersonalData;
import com.primitive_social_media.Post;
import javafx.geometry.Pos;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by apatters on 6/17/2017.
 */
public class MockDatabaseService extends DatabaseService{

    // personal data of personalData
    private HashMap<String, PersonalData> personalDataMap = new HashMap<>();
    // passwords of each personalData
    private HashMap<String, String> passwordMap = new HashMap<>();
    // own posts of personalData
    private HashMap<String, ArrayList<Post>> ownPostsMap = new HashMap<>();
    // messages sent to each user
    private HashMap<String, ArrayList<Post>> messagesMap = new HashMap<>();
    // array of personalData following a user
    private HashMap<String, ArrayList<String>> followedByMap = new HashMap<>();
    // array of personalData that a user follows
    private HashMap<String, ArrayList<String>> followingMap = new HashMap<>();

    public void  connect(){
        String username;
        username = "Bob";
        addUser(username,
                new PersonalData(username, username + "'s town", "01/02/2000", username+"'s business", username+"'s picture"),
                 username+"'s password");
        addPost(username, new Post(username, username+"'s post 1"));
        addPost(username, new Post(username, username+"'s post 2"));

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
    }

    @Override
    public void close() {

    }

    public void addUser(String username, PersonalData personalData, String password){
        if (personalDataMap.containsKey(username)) {
            String msg = String.format("user %s alread exists", username);
            throw new Error(msg);
        }

        personalDataMap.put(username, personalData);
        passwordMap.put(username, password);
        ownPostsMap.put(username, new ArrayList<>());
        messagesMap.put(username, new ArrayList<>());
        followedByMap.put(username, new ArrayList<>());
        followingMap.put(username, new ArrayList<>());
    }

    public Boolean checkUser(String username){
        return personalDataMap.containsKey(username);
    }
    public void deleteUser(String username){
        personalDataMap.remove(username);
        passwordMap.remove(username);
        ownPostsMap.remove(username);
        messagesMap.remove(username);
        followedByMap.remove(username);
        followingMap.remove(username);
        // consider how to update the follow maps of other users that include the deleted user
    }

    /**
     * find the personal data for a single user
     * @param username the user's username
     * @return the personal data
     */
    public PersonalData findPersonalData(String username){
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
        for(HashMap.Entry<String, PersonalData> entry: personalDataMap.entrySet()){
            String key = entry.getKey();
            if(key.matches(query)){
                matches.put(key, new PersonalData(personalDataMap.get(key)));
            }
        }
    return matches;
    }

    /**
     * set the personal data of a user
     * @param username - user's username
     * @param personalData - personal data
     */
    public void setPersonalData(String username, PersonalData personalData){
        personalDataMap.put(username, personalData);
    }


    /**
     * get a user's password
     * @param username
     * @return password
     */
    public String getPassword(String username){
        return passwordMap.get(username);
    }

    /**
     * set the password of a user
     * @param username
     * @param password - updated password
     */
    public void setPassword(String username, String password){
        passwordMap.put(username, password);
    }




    public ArrayList<Post> getOwnPosts(String username){
        String query = ("^"+username+"$");
        ArrayList<Post> matches =  getOwnPostsMultiple(query);
        // consider checking if user is not found
        return matches;
    }
    // consider removing
    public ArrayList<Post> getOwnPostsMultiple(String query){
        ArrayList<Post> matches = new ArrayList<>();
        for(HashMap.Entry<String, ArrayList<Post>> entry: ownPostsMap.entrySet()){
            String username = entry.getKey();
            if(username.matches(query)){
                ArrayList<Post> ownPosts = ownPostsMap.get(username);
                ownPosts.forEach(post->matches.add(new Post(post)));
            }
        }
        return matches;
    }

    public ArrayList<Post> getFollowedPosts(String username){
        ArrayList<Post> followedPosts = new ArrayList<>();
        followingMap.get(username).forEach(name->{
            ownPostsMap.get(name).forEach(post->followedPosts.add(post));
        });
        return followedPosts;
    }

    public void addPost(String username, Post post){
        ownPostsMap.get(username).add(post);
    }

    public void deletePost(String username, int idx){
        ownPostsMap.get(username).remove(idx);
    }





    public ArrayList<Post> getMessages(String username){
        return messagesMap.get(username);
    }

    public void addMessage(String username, Post message){
        messagesMap.get(username).add(message);
    }

    public void deleteMessage(String username, int idx){
        messagesMap.get(username).remove(idx);
    }


    public ArrayList<String> getFollowedBy(String username){
        return followedByMap.get(username);
    }

    public void addFollower(String followeeUsername, String followerUsername){
        ArrayList<String> followedBy = followedByMap.get(followeeUsername);
        if(!followedBy.contains(followerUsername)){
            followedBy.add(followerUsername);
            followingMap.get(followerUsername).add(followeeUsername);
        }
    }

    public void deleteFollower(String followeeUsername, String followerUsername){
        ArrayList<String> followedBy = followedByMap.get(followeeUsername);
        int idx = followedBy.indexOf(followerUsername);
        if(!(idx==-1)){
            followedBy.remove(followerUsername);
            followingMap.get(followerUsername).remove(followeeUsername);
        }
    }


    protected void addFollowee(String followeeUsername, String followerUsername){
        ArrayList<String> following = followingMap.get(followerUsername);
        if(!following.contains(followeeUsername)){
            following.add(followeeUsername);
        }
    }

    protected void deleteFollowee(String followeeUsername, String followerUsername){
        ArrayList<String> following = followedByMap.get(followerUsername);
        int idx = following.indexOf(followeeUsername);
        if(!(idx==-1)){
            following.remove(followeeUsername);
        }
    }


}
