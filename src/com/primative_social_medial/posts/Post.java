package com.primative_social_medial.posts;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by apatters on 6/11/2017.
 */
class Post{

    private String poster;
    private String content;

    public static String toJSON(Stack<Post> posts){
        String postsInner = "";

        Iterator<Post> postItr = posts.iterator();
        while(postItr.hasNext()){
            Post currentPost = postItr.next();
            if(postItr.hasNext()){
                postsInner += String.format("%s, ", currentPost.toJSONString());
            }else{
                postsInner += currentPost.toJSONString();
            }
        }

        return String.format("[%s]", postsInner);
    }

    Post(String poster, String content){
        this.poster=poster;
        this.content=content;
    }

    public String toJSONString(){
        return String.format("{\"poster\":\"%s\", \"content\":\"%s\"}",poster, content);
    }
}
