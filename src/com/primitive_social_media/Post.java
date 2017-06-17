package com.primitive_social_media;

import javafx.scene.input.DataFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Created by apatters on 6/11/2017.
 */
public class Post extends JSONConvertible{

    public String poster;
    public String content;
    public Date timestamp;

//    public static String toJSON(ArrayList<Post> posts){
//        String postsInner = "";
//
//        Iterator<Post> postItr = posts.iterator();
//        while(postItr.hasNext()){
//            Post currentPost = postItr.next();
//            if(postItr.hasNext()){
//                postsInner += String.format("%s, ", currentPost.toJSON());
//            }else{
//                postsInner += currentPost.toJSON();
//            }
//        }
//
//        return String.format("[%s]", postsInner);
//    }

    public Post(String poster, String content, Date timestamp){
        this.poster=poster;
        this.content=content;
        this.timestamp = timestamp;
    }

    public Post(String poster, String content){
        this(poster, content, new Date());
    }

    public Post(Post post){
        this.poster = post.poster;
        this.content = post.content;
        this.timestamp = post.timestamp;
    }


    public String toJSON(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String timestampStr = df.format(this.timestamp);
        return String.format("{\"poster\":\"%s\", \"content\":\"%s\", \"timestamp\":%s}",poster, content, timestampStr);
    }
}
