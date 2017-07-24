package com.primitive_social_media.test;

import com.primitive_social_media.PersonalData;
import com.primitive_social_media.Post;
import com.primitive_social_media.UserData;
import com.primitive_social_media.database.*;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.exception.InvalidDataException;
import com.primitive_social_media.exception.UserNotExistsException;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by apatters on 6/19/2017.
 */
public class MockDatabaseServiceTest{

    private MockDatabaseService mockDatabaseService = MockDatabaseService.getInstance();


    @After
    public void close(){
        mockDatabaseService.close();
    }

    @Test
    public void testAddUser(){


        assertEquals(mockDatabaseService.findPersonalDataMultiple(".*").size(), 0);

        UserData newUser = addNewUser();;

        HashMap<String, PersonalData> updatedUsers = mockDatabaseService.findPersonalDataMultiple(".*");
        assertEquals(updatedUsers.size(), 1);
        assertTrue(updatedUsers.containsKey(newUser.personalData.name));
        assertEquals(updatedUsers.get(newUser.personalData.name).name, newUser.personalData.name);
    }

    @Test
    public void testDeleteUser() throws UserNotExistsException {

        UserData newUser = addNewUser();
        String username = newUser.personalData.name;

        assertEquals(mockDatabaseService.userData.size(), 1);
        mockDatabaseService.deleteUser(username);
        assertEquals(mockDatabaseService.userData.size(), 0);
    }

    @Test
    public void testFindUser() throws UserNotExistsException {
        UserData newUser = addNewUser();
        String username = newUser.personalData.name;

        PersonalData personalData = mockDatabaseService.findPersonalData(username);
        assertEquals(personalData.name, username);
    }

    @Test
    public void testFindUserMultiple(){
        addNewUser("Anne");
        addNewUser("Bill");

        String query = "^[AB].*";
        HashMap<String, PersonalData> personalData = mockDatabaseService.findPersonalDataMultiple(query);
        assertEquals(personalData.size(), 2);
    }

    @Test
    public void testSetPersonalData() throws UserNotExistsException {
        UserData newUser = addNewUser();
        String username = newUser.personalData.name;
        String location = newUser.personalData.location;

        PersonalData newPersonalData = new PersonalData(username, "junk", "2000-01-02", "d", "e");
        assertNotEquals(mockDatabaseService.findPersonalData(username).location, newPersonalData.location);

        mockDatabaseService.setPersonalData(username, newPersonalData);
        assertEquals(mockDatabaseService.findPersonalData(username).location, newPersonalData.location);
    }

    @Test
    public void testGetPassword() throws UserNotExistsException {
        UserData newUser = addNewUser();
        String username = newUser.personalData.name;
        String password = newUser.password;
        assertEquals(mockDatabaseService.getPassword(username), password);
    }

    @Test
    public void testSetPassword() throws UserNotExistsException {
        UserData newUser = addNewUser();
        String username = newUser.personalData.name;
        String newPassword = "newPassword";
        assertNotEquals(mockDatabaseService.getPassword(username), newPassword);
        mockDatabaseService.setPassword(username, newPassword);
        assertEquals(mockDatabaseService.getPassword(username), newPassword);
    }

    @Test
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

    @Test
    public void testDeletetPost() throws UserNotExistsException, InvalidDataException {
        UserData newUser = addNewUser();
        String username = newUser.personalData.name;

        Post newPost = new Post(username, "post");
        mockDatabaseService.addPost(username, newPost);

        mockDatabaseService.deletePost(username, 0);
        assertEquals(mockDatabaseService.getOwnPosts(username).size(), 0);
    }

    @Test
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

        assertEquals(1, mockDatabaseService.getFollowedPosts(anneName).size());
        assertEquals(2, mockDatabaseService.getFollowedPosts(billName).size());

        mockDatabaseService.deleteFollower(anneName, billName);
        assertEquals(1, mockDatabaseService.getFollowedPosts(billName).size());
    }

    @Test
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
//        PersonalData personalData = new PersonalData(username, "b", "01/02/2000", "d", "e");
        PersonalData personalData = new PersonalData(username, "b", "2000-01-02", "d", "e");

        String password = "password";

        mockDatabaseService.addUser(username, personalData, password);

        return new UserData(username, password, personalData);
    }
}
