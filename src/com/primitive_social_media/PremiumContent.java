package com.primitive_social_media;

/**
 * Created by apatters on 7/3/2017.
 */
public class PremiumContent extends JSONConvertible {

    public String content;

    public PremiumContent(String content){
        this.content = content;
    }

    @Override
    public String toJSON() {
        return String.format("{\"content\":\"%s\"}",content);
    }
}
