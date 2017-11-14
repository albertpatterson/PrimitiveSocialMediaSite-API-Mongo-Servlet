package com.primitive_social_media;

/**
 * @desc Representation of premium content
 */
public class PremiumContent extends JSONConvertible {

    public String content;

    /**
     * create an item of premium content
     * @param content - the content to include
     */
    public PremiumContent(String content){
        this.content = content;
    }

    @Override
    public String toJSON() {
        return String.format("{\"content\":\"%s\"}",content);
    }
}
