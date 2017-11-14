package com.primitive_social_media;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @desc Personal data of a user
 */
public class PersonalData extends JSONConvertible {
    public String name;
    public String location;
    public Date DOB;
    private static SimpleDateFormat DOBFormatter = new SimpleDateFormat("MM/dd/yyyy");
    public String business;
    public String picture;

    /**
     * Create an item of personal data for a user
     * @param name - username
     * @param location - location
     * @param DOBString - date of birth
     * @param business - business
     * @param picture - profile picture
     */
    public PersonalData(String name, String location, String DOBString, String business, String picture){
        this.name = name;
        this.location = location;
        this.business = business;
        this.picture = picture;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.DOB = formatter.parse(DOBString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create an item of personal data for a user
     * @param name - username
     * @param location - location
     * @param DOB - date of birth
     * @param business - business
     * @param picture - profile picture
     */
    public PersonalData(String name, String location, Date DOB, String business, String picture){
        this.name = name;
        this.location = location;
        this.DOB = DOB;
        this.business = business;
        this.picture = picture;
    }

    /**
     * clone an item of persnal data
     * @param data cloned personal data
     */
    public PersonalData(PersonalData data){
        this(data.name, data.location, data.DOB, data.business, data.picture);
    }

    /**
     * get the date of birth as a string
     * @return string representing the data of birth
     */
    public String getDOBString(){
        return PersonalData.DOBFormatter.format(DOB);
    }

    /**
     * set the date of birth string
     * @param DOBString the date of birth
     * @throws ParseException
     */
    public void setDOBString(String DOBString) throws ParseException {
        DOB = PersonalData.DOBFormatter.parse(DOBString);
    }

    @Override
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
