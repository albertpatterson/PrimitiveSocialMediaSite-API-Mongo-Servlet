package com.primitive_social_media.posts;


import com.primitive_social_media.Post;

import java.util.ArrayList;

/**
 * Created by apatters on 6/11/2017.
 */
public class MockPostManager {

    public ArrayList<Post> posts = new ArrayList(5);

    public MockPostManager(){
        posts.add(new Post("Carl","Post by Carl"));
        posts.add(new Post("Bert","Post by bert"));
        posts.add(new Post("Kim","Post by Kim"));
        posts.add(new Post("Sam","Post by Sam"));
        posts.add(new Post("Anne","Post by Anne"));
    }

    public void connect(){

    }

    public void addPost(String poster, String content){
        posts.add(new Post(poster,content));
    }

    public void deletePost(int index){
        posts.remove(index);
    }

    public String getPosts(){
       return Post.toJSON(posts);
    }

    public void close(){

    }
}
