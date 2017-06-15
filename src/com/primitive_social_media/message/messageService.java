package com.primitive_social_media.message;

import com.primitive_social_media.Post;
import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.users.UserService;

/**
 * Created by apatters on 6/13/2017.
 */
public class MessageService {

    private DatabaseService databaseService = MockDatabaseService.getInstance();
    private UserService userService = UserService.getInstance();


    private static MessageService instance = null;
    public static MessageService getInstance(){
        if(instance == null){
            instance = new MessageService();
        }

        return instance;
    }

    private MessageService(){

    }

    public void addMessage(String sender, String content, String recipient){
        int idx = databaseService.addPost(new Post(sender, content));
        userService.addMessage(userService.findUser(recipient).name, idx);
    }


}
