package com.primitive_social_media.database;

import com.primitive_social_media.Post;
import com.primitive_social_media.User;
import com.primitive_social_media.message.MessageService;
import com.primitive_social_media.posts.PostService;
import com.primitive_social_media.users.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by apatters on 6/13/2017.
 */
public class MockDatabaseService implements DatabaseService {

    private Boolean isConnected = false;

    private static UserService userService = UserService.getInstance();
    private static PostService postService = PostService.getInstance();
    private static MessageService messageService = MessageService.getInstance();

    HashMap<String,User> users = new HashMap<String, User>();
    ArrayList<Post> posts = new ArrayList<Post>();

    private static MockDatabaseService instance = null;

    public static MockDatabaseService getInstance(){
        if (instance ==null) {
            instance = new MockDatabaseService();
        }

        return instance;
    }

    private MockDatabaseService(){

    }

    private void initialize(){

        // add users
        userService.addUser( new User("Cam", "PW1", "Natick", new Date(), "Welding", "Cam's Pick"));
        userService.addUser( new User("Kim", "PW2", "Junk", new Date(), "Teaching", "Jim's Pick"));
        userService.addUser( new User("Sam", "PW3", "Place", new Date(), "Sweeping", "Sam's Pick"));
        userService.addUser( new User("Anne", "PW4", "Car", new Date(), "Speaking", "Anne's Pick"));


        // add posts
        postService.addPost("Cam","Post1 by Cam");
        postService.addPost("Cam","Post2 by Cam");
        postService.addPost("Kim","Post by Kim");
        postService.addPost("Sam","Post by Sam");
        postService.addPost("Anne","Post by Anne");

        // add messages
//        messageService.addMessage("Cam","Message by Cam", "Sam");
//        messageService.addMessage("Cam","Message by Cam", "Kim");
//        messageService.addMessage("Kim","Message by Kim", "Kim");
//        messageService.addMessage("Sam","Message by Sam", "Kim");
//        messageService.addMessage("Anne","Message by Anne", "Cam");
    }

    public void connect(){
        if(!isConnected){
            initialize();
            isConnected = true;
        }
    }

    public void close(){

    }

    @Override
    public Boolean validateCredentials(String username, String password) {
        User user = userService.findUser(username);
        return user.password.equals(password);
    }

    public HashMap<String, User> findUsers(String query){
        HashMap<String, User> matches = new HashMap<>();
        for(HashMap.Entry<String, User> entry: users.entrySet()){
            String key = entry.getKey();
            if(key.matches(query)){
                matches.put(key, users.get(key));
            }
        }
        return matches;
    }

    public ArrayList<Post> getPosts(ArrayList<Integer> indexes){
        ArrayList<Post> matchingPosts = new ArrayList<>();
        indexes.forEach((index)->matchingPosts.add(posts.get(index)));
        return matchingPosts;
//        return posts.get(username);
    }

    public void updateUser(User user){
        users.put(user.name, user);
    }

    public int addPost(Post newPost){
        posts.add(newPost);
        return posts.size()-1;
    }


    public void deleteUser(String username){
        users.remove(username);
    }

    public void deletePost(int postIdx){
        // need to think of way of managing posts so the deleted posts are accounted for correctly
//        posts.remove(postIdx);
        posts.get(postIdx).clear();
    }

    public int countPosts(){
        return posts.size();
    }

}
