package com.primative_social_medial.posts;


import java.util.Stack;

/**
 * Created by apatters on 6/11/2017.
 */
public class MockPostManager {

    public Stack<Post> posts = new Stack();

    public MockPostManager(){
        posts.push(new Post("Carl","Post by Carl"));
        posts.push(new Post("Bert","Post by bert"));
        posts.push(new Post("Kim","Post by Kim"));
        posts.push(new Post("Sam","Post by Sam"));
        posts.push(new Post("Anne","Post by Anne"));
    }

    public void connect(){

    }

    public void addPost(String poster, String content){
        posts.push(new Post(poster,content));
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
