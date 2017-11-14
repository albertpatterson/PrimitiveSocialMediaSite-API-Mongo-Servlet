package com.primitive_social_media;

import javafx.scene.input.DataFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * @desc Representation of a public post by a user
 */
public class Post extends JSONConvertible{

    public String poster;
    public String content;
    public Date timestamp;


    /**
     * create a new post
     * @param poster - the name of the user creating the post
     * @param content - the text content of the post
     * @param timestamp - the time at which the post was created
     */
    public Post(String poster, String content, Date timestamp){
        this.poster=poster;
        this.content=content;
        this.timestamp = timestamp;
    }

    /**
     * create a new post with default timestamp
     * @param poster - the name of the user creating the post
     * @param content - the text content of the post
     */
    public Post(String poster, String content){
        this(poster, content, new Date());
    }

    /**
     * clone a post
     * @param post the post to clone
     */
    public Post(Post post){
        this.poster = post.poster;
        this.content = post.content;
        this.timestamp = post.timestamp;
    }

    @Override
    public String toJSON(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String timestampStr = df.format(this.timestamp);
        return String.format("{\"poster\":\"%s\", \"content\":\"%s\", \"timestamp\":\"%s\"}",poster, content, timestampStr);
    }
}
