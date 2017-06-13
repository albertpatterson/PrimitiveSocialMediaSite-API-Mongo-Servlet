package com.primitive_social_media.database;

import com.primitive_social_media.Post;
import com.primitive_social_media.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by apatters on 6/13/2017.
 */
public class MockDatabaseService implements DatabaseService {

    HashMap<String,User> users;
    HashMap<String, ArrayList<Post>> posts;
    HashMap<String, ArrayList<Post>> messages;

    Boolean isConnected;

    public MockDatabaseService(){
        updateUser( new User("Cam", "PW1", "Natick", new Date(), "Welding", "Cam's Pick"));
        updateUser( new User("Jim", "PW2", "Junk", new Date(), "Teaching", "Jim's Pick"));
        updateUser( new User("Sam", "PW3", "Place", new Date(), "Sweeping", "Sam's Pick"));
        updateUser( new User("Anne", "PW4", "Car", new Date(), "Speaking", "Anne's Pick"));

        addPost(new Post("Carl","Post by Carl"), "Cam");
        addPost(new Post("Bert","Post by bert"), "Cam");
        addPost(new Post("Kim","Post by Kim"), "Cam");
        addPost(new Post("Sam","Post by Sam"), "Cam");
        addPost(new Post("Anne","Post by Anne"), "Cam");

        addMessage(new Post("Bert","Message From bert"), "Cam");
        addMessage(new Post("Kim","Message From Kim"), "Cam");
        addMessage(new Post("Sam","Message From Sam"), "Cam");
        addMessage(new Post("Anne","Message From Anne"), "Cam");
    }

    public void connect(){
        if(!isConnected){
            isConnected = true;
        }
    }

    public void close(){
        if(isConnected){
            isConnected = false;
        }
    }

    @Override
    public Boolean validateCredentials(String username, String password) {
        return users.get(username).password.equals(password);
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

    public ArrayList<Post> getPosts(String username){
        return posts.get(username);
    }

    public ArrayList<Post> getMessages(String username){
        return messages.get(username);
    }

    public void updateUser(User user){
        users.put(user.name, user);
    }

    public void addPost(Post newPost, String username){
        posts.get(username).add(newPost);
    }

    public void addMessage(Post newMessage, String username){
        messages.get(username).add(newMessage);
    }

    public void deleteUser(String username){
        users.remove(username);
        posts.remove(username);
        messages.remove(username);
    }

    public void deletePost(String username, int postIdx){
        posts.get(username).remove(postIdx);
    }

    public void deleteMessage(String username, int messageIdx){
        messages.get(username).remove(messageIdx);
    }


}
