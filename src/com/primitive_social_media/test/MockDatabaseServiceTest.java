package com.primitive_social_media.test;

import com.primitive_social_media.PersonalData;
import com.primitive_social_media.Post;
import com.primitive_social_media.UserData;
import com.primitive_social_media.database.*;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.exception.InvalidDataException;
import com.primitive_social_media.exception.UserNotExistsException;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by apatters on 6/19/2017.
 */
public class MockDatabaseServiceTest extends TestCase{

    private com.primitive_social_media.database.MockDatabaseService mockDatabaseService = new MockDatabaseService();


    public void testAddUser(){


        assertEquals(mockDatabaseService.findPersonalDataMultiple(".*").size(), 0);

        UserData newUser = addNewUser();;

        HashMap<String, PersonalData> updatedUsers = mockDatabaseService.findPersonalDataMultiple(".*");
        assertEquals(updatedUsers.size(), 1);
        assertTrue(updatedUsers.containsKey(newUser.personalData.name));
        assertEquals(updatedUsers.get(newUser.personalData.name).name, newUser.personalData.name);
    }


    public void testDeleteUser() throws UserNotExistsException {

        UserData newUser = addNewUser();
        String username = newUser.personalData.name;

        assertEquals(mockDatabaseService.userData.size(), 1);
        mockDatabaseService.deleteUser(username);
        assertEquals(mockDatabaseService.userData.size(), 0);
    }


    public void testFindUser() throws UserNotExistsException {
        UserData newUser = addNewUser();
        String username = newUser.personalData.name;

        PersonalData personalData = mockDatabaseService.findPersonalData(username);
        assertEquals(personalData.name, username);
    }


    public void testFindUserMultiple(){
        addNewUser("Anne");
        addNewUser("Bill");

        String query = "^[AB].*";
        HashMap<String, PersonalData> personalData = mockDatabaseService.findPersonalDataMultiple(query);
        assertEquals(personalData.size(), 2);
    }


    public void testSetPersonalData() throws UserNotExistsException {
        UserData newUser = addNewUser();
        String username = newUser.personalData.name;
        String location = newUser.personalData.location;

        PersonalData newPersonalData = new PersonalData(username, "junk", "01/02/2000", "d", "e");
        assertNotEquals(mockDatabaseService.findPersonalData(username).location, newPersonalData.location);

        mockDatabaseService.setPersonalData(username, newPersonalData);
        assertEquals(mockDatabaseService.findPersonalData(username).location, newPersonalData.location);
    }


    public void testGetPassword() throws UserNotExistsException {
        UserData newUser = addNewUser();
        String username = newUser.personalData.name;
        String password = newUser.password;
        assertEquals(mockDatabaseService.getPassword(username), password);
    }


    public void testSetPassword() throws UserNotExistsException {
        UserData newUser = addNewUser();
        String username = newUser.personalData.name;
        String newPassword = "newPassword";
        assertNotEquals(mockDatabaseService.getPassword(username), newPassword);
        mockDatabaseService.setPassword(username, newPassword);
        assertEquals(mockDatabaseService.getPassword(username), newPassword);
    }


    public void testAddAndGetPost() throws UserNotExistsException {
        UserData newUser = addNewUser();
        String username = newUser.personalData.name;
        assertEquals(mockDatabaseService.getOwnPosts(username).size(), 0);

        Post newPost = new Post(username, "post");
        mockDatabaseService.addPost(username, newPost);

        ArrayList<Post> ownPosts = mockDatabaseService.getOwnPosts(username);
        assertEquals(ownPosts.size(), 1);
        assertEquals(ownPosts.get(0).content, newPost.content);
    }


    public void testDeletetPost() throws UserNotExistsException, InvalidDataException {
        UserData newUser = addNewUser();
        String username = newUser.personalData.name;

        Post newPost = new Post(username, "post");
        mockDatabaseService.addPost(username, newPost);

        mockDatabaseService.deletePost(username, 0);
        assertEquals(mockDatabaseService.getOwnPosts(username).size(), 0);
    }


    public void testFollowUnfollowUserAndGetFollowedPosts() throws UserNotExistsException {
        UserData anne = addNewUser("Anne");
        String anneName = anne.personalData.name;

        UserData bill = addNewUser("Bill");
        String billName = bill.personalData.name;


        assertEquals(anne.followedBy.size(), 0);
        assertEquals(anne.following.size(), 0);

        assertEquals(bill.followedBy.size(), 0);
        assertEquals(bill.following.size(), 0);

        mockDatabaseService.addFollower(anneName, billName);
//        assertEquals(mockDatabaseService.getFollowedBy(anneName).size(), 1);
//        assertEquals(mockDatabaseService.getFollowedBy(billName).size(), 0);


        Post billPost = new Post(billName, "bills post");
        mockDatabaseService.addPost(billName, billPost);

        Post annePost = new Post(anneName, "anne's post");
        mockDatabaseService.addPost(anneName, annePost);

        assertEquals(mockDatabaseService.getFollowedPosts(anneName).size(), 0);
        assertEquals(mockDatabaseService.getFollowedPosts(billName).size(), 1);


        mockDatabaseService.deleteFollower(anneName, billName);
        assertEquals(mockDatabaseService.getFollowedPosts(billName).size(), 0);
    }


    public void testAddGetDeleteMessage() throws UserNotExistsException, InvalidDataException {
        UserData newUser = addNewUser();
        String username = newUser.personalData.name;

        assertEquals(mockDatabaseService.getMessages(username).size(), 0);

        String otherUsername = "other";
        Post othersMessage = new Post(otherUsername, otherUsername+ "'s message");
        mockDatabaseService.addMessage(username, othersMessage);

        assertEquals(mockDatabaseService.getMessages(username).size(), 1);

        mockDatabaseService.deleteMessage(username,0);

        assertEquals(mockDatabaseService.getMessages(username).size(), 0);
    }


    public UserData addNewUser(){
        return addNewUser("Anne");
    }


    public UserData addNewUser(String username){
        PersonalData personalData = new PersonalData(username, "b", "01/02/2000", "d", "e");
        String password = "password";

        mockDatabaseService.addUser(username, personalData, password);

        return new UserData(username, password, personalData);
    }
}
