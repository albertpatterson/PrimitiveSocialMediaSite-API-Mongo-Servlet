package com.primitive_social_media.posts;


import com.primitive_social_media.Post;
import com.primitive_social_media.User;
import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.users.UserService;
import javafx.geometry.Pos;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by apatters on 6/11/2017.
 */
public class PostService {

    private DatabaseService databaseService = MockDatabaseService.getInstance();
    private UserService userService = UserService.getInstance();

    private static PostService instance = null;
    public static PostService getInstance(){
        if(instance == null){
            instance = new PostService();
        }

        return instance;
    }

    private PostService(){
    }


    public void connect(){
    }


    public ArrayList<Post> getOwnPosts(String poster){
        User user = userService.findUser(poster);
        return databaseService.getPosts(user.ownPosts);
    }


    public ArrayList<Post> getFollowedPosts(String poster){
        return databaseService.getPosts(userService.findUser(poster).followedPosts);
    }


    public void addPost(String poster, String content){
        // update own posts;
        int idx = databaseService.addPost(new Post(poster,content));
        userService.addOwnPost(poster, idx);
        ArrayList<String> followers = userService.getFollowers(poster);

        // update followers
        followers.forEach((name)->userService.addFollowedPost(userService.findUser(name).name, idx));
    }


    public void deletePost(String username, int userIndex){
        User user = userService.findUser(username);
        int index = user.ownPosts.get(userIndex);
        user.ownPosts.remove(userIndex);
        databaseService.deletePost(index);
    }


    public void close(){

    }
}
