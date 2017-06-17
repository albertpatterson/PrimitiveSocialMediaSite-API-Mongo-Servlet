package com.primitive_social_media.message;

import com.primitive_social_media.JSONConvertible;
import com.primitive_social_media.Post;
import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.sessions.SessionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by apatters on 6/13/2017.
 */
public class MessageServlet {

    private SessionService sessionService = new SessionService();
    private DatabaseService databaseService = new MockDatabaseService();

    // connect to database on init
    public void init(){
        databaseService.connect();
        System.out.println("Initialized Post Servlet");
    }


    protected void addMessageAfterAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        String poster = request.getParameter("poster");
        String content = request.getParameter("content");

        databaseService.addMessage(poster, new Post(poster, content));

        PrintWriter out = response.getWriter();
        out.println("Success");
        out.flush();

        System.out.println("Created a Message");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sessionService.validateThenRespond(request, response, ()->addMessageAfterAuth(request, response));
    }





    protected void getMessagesAfterAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArrayList<Post> messages;

        String username = request.getParameter("username");
        messages = databaseService.getFollowedPosts(username);

        String JSON = JSONConvertible.toJSONList(messages);

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(JSON);
        out.flush();
        System.out.println("Sent Posts");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get posts");

        sessionService.validateThenRespond(request, response, ()->getMessagesAfterAuth(request, response));
    }





    protected void deleteMessageAfterAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");

        int index = Integer.parseInt(request.getParameter("index"));
        databaseService.deleteMessage(username, index);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("Success");
        out.flush();

        System.out.println("Deleted a Post");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionService.validateThenRespond(request, response, ()->deleteMessageAfterAuth(request, response));
    }





    // close connection to database on destroy
    public void destroy(){
        databaseService.close();
        System.out.println("Destroyed");
    }
}
