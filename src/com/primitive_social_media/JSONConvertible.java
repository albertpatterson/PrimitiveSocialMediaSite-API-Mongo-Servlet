package com.primitive_social_media;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @desc Object containing data that can be converted to a JSON string
 */
public abstract class JSONConvertible {

    /**
     * create a JSON string representing the object
     * @return JSON string representing the object
     */
    public abstract String toJSON();

    /**
     * create a JSON string representing an array of objects
     * @param jsonStrings - JSON strings representing objects
     * @return JSON string representing the array of objects
     */
    public static String toJSONListFromStrings(ArrayList<String> jsonStrings) {
        String innerJSON = "";

        Iterator<String> jsonStringIter = jsonStrings.iterator();
        while (jsonStringIter.hasNext()) {
            String jsonString = jsonStringIter.next();
            innerJSON += (jsonStringIter.hasNext() ? (jsonString + ", ") : jsonString);
        }

        return String.format("{\"data\": [%s]}", innerJSON);
    }

    /**
     * create a JSON string representing an array of objects
     * @param itemList - list of items to be represented as  JSON string
     * @return JSON string representing the array of objects
     */
    public static String toJSONList(ArrayList<? extends JSONConvertible> itemList){
        String innerJSON = "";
        ArrayList<String> jsonStrings = new ArrayList<>(itemList.size());
        for(JSONConvertible item : itemList){
            jsonStrings.add(item.toJSON());
        }

        return JSONConvertible.toJSONListFromStrings(jsonStrings);
    }
}
