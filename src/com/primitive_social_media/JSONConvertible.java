package com.primitive_social_media;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by apatters on 6/17/2017.
 */
public abstract class JSONConvertible {
    public abstract String toJSON();

    public static String toJSONList(ArrayList<? extends JSONConvertible> itemList){
        String innerJSON = "";

        Iterator<JSONConvertible> itemIter = (Iterator<JSONConvertible>) itemList.iterator();
        while(itemIter.hasNext()){
            JSONConvertible item = itemIter.next();
            if(itemIter.hasNext()){
                innerJSON += String.format("%s, ", item.toJSON());
            }else{
                innerJSON += item.toJSON();
            }
        }

        return String.format("{\"data\": [%s]}", innerJSON);
    }
}
