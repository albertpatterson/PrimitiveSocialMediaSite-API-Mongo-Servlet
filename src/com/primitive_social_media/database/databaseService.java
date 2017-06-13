package com.primitive_social_media.database;

import com.primitive_social_media.Post;
import com.primitive_social_media.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by apatters on 6/13/2017.
 */
public interface DatabaseService {

    public void connect();

    public void close();

    public Boolean validateCredentials(String username, String password);

    public HashMap<String, User> findUsers(String query);

    public ArrayList<Post> getPosts(String username);

    public ArrayList<Post> getMessages(String username);

    public void updateUser(User user);

    public void addPost(Post newPost, String username);

    public void addMessage(Post newMessage, String username);

    public void deleteUser(String username);

    public void deletePost(String username, int postIdx);

    public void deleteMessage(String username, int messageIdx);
}
