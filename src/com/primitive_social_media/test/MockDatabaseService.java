package com.primitive_social_media.test;

import com.primitive_social_media.JSONConvertible;
import com.primitive_social_media.PersonalData;
import com.primitive_social_media.Post;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by apatters on 6/17/2017.
 */
public class MockDatabaseService {

    private static com.primitive_social_media.database.MockDatabaseService databaseService = new com.primitive_social_media.database.MockDatabaseService();

    public static void main(String[] args){
        databaseService.connect();

        String JSON  = JSONConvertible.toJSONList(databaseService.getFollowedPosts("Raj"));
        System.out.println(JSON);

//        databaseService.addPost("Kim", new Post("Kim", "herp derp"));
//        databaseService.deletePost("Kim", 1);
//        ArrayList<Post> posts = databaseService.getFollowedPosts("Raj");
//        posts.forEach(post->{
//            System.out.println(post.poster);
//            System.out.println(post.content);
//            System.out.println(post.timestamp);
//        });
//
//        databaseService.getMessages("Kim").forEach(post->{
//            System.out.println(post.poster);
//            System.out.println(post.content);
//            System.out.println(post.timestamp);
//        });
//
//        databaseService.deleteMessage("Kim", 0);
//
//        databaseService.getMessages("Kim").forEach(post->{
//            System.out.println(post.poster);
//            System.out.println(post.content);
//            System.out.println(post.timestamp);
//        });

//        PersonalData data = databaseService.findPersonalData("Kim");
//        System.out.println(data.location);
//
//        PersonalData newData = new PersonalData(data);
//        newData.location = "Kim's new Location";
//        databaseService.setPersonalData("Kim", newData);
//
//        data = databaseService.findPersonalData("Kim");
//        System.out.println(data.location);


//        HashMap<String, PersonalData> datas = databaseService.findPersonalDataMultiple(".*");
//        datas.forEach((name, data)-> {
//            System.out.println(name);
//            System.out.println(data.name);
//            System.out.println(data.location);
//            System.out.println(data.business);
//            System.out.println(data.DOB);
//            System.out.println(data.picture);
//        });
    }
}
