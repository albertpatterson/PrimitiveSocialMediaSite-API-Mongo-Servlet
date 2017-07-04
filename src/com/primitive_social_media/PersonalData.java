package com.primitive_social_media;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by apatters on 6/17/2017.
 */
public class PersonalData extends JSONConvertible {
    public String name;
    public String location;
    public Date DOB;
    private static SimpleDateFormat DOBFormatter = new SimpleDateFormat("MM/dd/yyyy");
    public String business;
    public String picture;

    public PersonalData(String name, String location, String DOBString, String business, String picture){
        this.name = name;
        this.location = location;
        this.business = business;
        this.picture = picture;

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            this.DOB = formatter.parse(DOBString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public PersonalData(String name, String location, Date DOB, String business, String picture){
        this.name = name;
        this.location = location;
        this.DOB = DOB;
        this.business = business;
        this.picture = picture;
    }

    public PersonalData(PersonalData data){
        this(data.name, data.location, data.DOB, data.business, data.picture);
    }

    public String getDOBString(){
        return PersonalData.DOBFormatter.format(DOB);
    }

    public void setDOBString(String DOBString) throws ParseException {
        DOB = PersonalData.DOBFormatter.parse(DOBString);
    }

    public String toJSON(){
        return  "{" +
                "\"name\": \"" +       name +"\", "+
                "\"location\": \"" +   location +"\", "+
                "\"DOB\": \"" +        getDOBString() +"\", "+
                "\"business\": \"" +   business +"\", "+
                "\"picture\": \"" +    picture +"\""+
                "}";
    }
}
