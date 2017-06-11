package com.primitive_social_media.users;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by apatters on 6/11/2017.
 */
public class MockUserManager {

    private HashMap<String, User> users = new HashMap();

    public MockUserManager(){
        putUser( new User("Cam", "PW1", "Natick", new Date(), "Welding", "Cam's Pick"));
        putUser( new User("Jim", "PW2", "Junk", new Date(), "Teaching", "Jim's Pick"));
        putUser( new User("Sam", "PW3", "Place", new Date(), "Sweeping", "Sam's Pick"));
        putUser( new User("Anne", "PW4", "Car", new Date(), "Speaking", "Anne's Pick"));
    }

    public void connect(){}

    public HashMap<String, User> getUsers(String query){
        return users;
    }

    public void putUser(User userData){
        users.put(userData.name, userData);
    }

    public void deleteUser(String name){
        users.remove(name);
    }

    public void close(){}
}
