package com.primitive_social_media.posts;

import com.primitive_social_media.JSONConvertible;
import com.primitive_social_media.Post;
import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.sessions.SessionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by apatters on 6/11/2017.
 */
@WebServlet(name = "PersonalDataServlet")
public class PostsServlet extends HttpServlet {

    private SessionService sessionService = new SessionService();
    private DatabaseService databaseService = new MockDatabaseService();

    // connect to database on init
    public void init(){
        databaseService.connect();
        System.out.println("Initialized Post Servlet");
    }


    protected void addPostAfterAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        String poster = request.getParameter("poster");
        String content = request.getParameter("content");

        databaseService.addPost(poster, new Post(poster, content));

        response.setStatus(HttpServletResponse.SC_CREATED);

        System.out.println("Created a Post");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sessionService.validateThenRespond(request, response, ()->addPostAfterAuth(request, response));
    }





    protected void getPostsAfterAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String postType = request.getParameter("type");
        ArrayList<Post> posts;

        switch(postType) {
            case "followed":
                String username = request.getParameter("username");
                posts = databaseService.getFollowedPosts(username);
                break;
            case "own":
                String poster = request.getParameter("poster");
                posts = databaseService.getOwnPosts(poster);
                break;
            default:
                String msg = String.format("Invalid post type requested: %s", postType);
                System.out.println(msg);
                throw new Error(msg);
        }

        String JSON = JSONConvertible.toJSONList(posts);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(JSON);
        out.flush();
        System.out.println("Sent Posts");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get posts");

        sessionService.validateThenRespond(request, response, ()->getPostsAfterAuth(request, response));
    }





    protected void deletePostAfterAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");

        int index = Integer.parseInt(request.getParameter("index"));
        databaseService.deletePost(username, index);

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        System.out.println("Deleted a Post");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionService.validateThenRespond(request, response, ()->deletePostAfterAuth(request, response));
    }





    // close connection to database on destroy
    public void destroy(){
        databaseService.close();
        System.out.println("Destroyed");
    }
}

