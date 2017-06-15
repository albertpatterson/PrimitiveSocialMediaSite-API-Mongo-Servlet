package com.primitive_social_media.users;

import com.primitive_social_media.User;
import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.sessions.SessionService;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by apatters on 6/14/2017.
 */
public class UserService {

    private DatabaseService databaseService = MockDatabaseService.getInstance();
    private SessionService sessionService = new SessionService();

    private static UserService instance = null;

    public static UserService getInstance(){
        if (instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    protected UserService(){
//        databaseService.connect();
    }

    public void connect(){
        databaseService.connect();
    }

    public HashMap<String, User> findUsers(String query){
        return databaseService.findUsers(query);
    }

    public User findUser(String username){
        String query = "^"+username+"$";
        HashMap<String, User> matches = findUsers(query);

        int nMatches = matches.size();
        if(nMatches==1) {
            return matches.get(username);
        }else{
            String msg = String.format("User %s not Found", username);
            System.out.println(msg);
            throw new Error(msg);
        }
    }


    public void addUser(User newUser){
        databaseService.updateUser(newUser);
    }

    public void updateUser(User user){
        databaseService.updateUser(user);
    }

    public void addOwnPost(String username, int index){
        User user = findUser(username);
        user.ownPosts.add(index);
        updateUser(user);
    }

    public void addFollowedPost(String username, int index){
        User user = findUser(username);
        user.followedPosts.add(index);
        updateUser(user);
    }

    public void addMessage(String username, int index){
        User user = findUser(username);
        user.messages.add(index);
        updateUser(user);
    }

    public void deleteUser(String username){
        databaseService.deleteUser(username);
    }

    public ArrayList<String> getFollowers(String username){
        User user = findUser(username);
        return user.followers;
    }





}
