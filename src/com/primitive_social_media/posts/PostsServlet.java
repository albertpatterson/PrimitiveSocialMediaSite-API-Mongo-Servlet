package com.primitive_social_media.posts;

import com.primitive_social_media.Post;
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
@WebServlet(name = "UsersServlet")
public class PostsServlet extends HttpServlet {

    private SessionService sessionService = new SessionService();
    public PostService postService = PostService.getInstance();





    // connect to database on init
    public void init(){
        postService.connect();
        System.out.println("Initialized Post Servlet");
    }





    protected void addPostAfterAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        String poster = request.getParameter("poster");
        String content = request.getParameter("content");

        postService.addPost(poster, content);

        PrintWriter out = response.getWriter();
        out.println("Success");
        out.flush();

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
                posts = postService.getFollowedPosts(username);
                break;
            case "own":
                String poster = request.getParameter("poster");
                posts = postService.getOwnPosts(poster);
                break;
            default:
                String msg = String.format("Invalid post type requested: %s", postType);
                System.out.println(msg);
                throw new Error(msg);
        }

        String JSON = Post.toJSON(posts);

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
        postService.deletePost(username, index);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("Success");
        out.flush();

        System.out.println("Deleted a Post");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionService.validateThenRespond(request, response, ()->deletePostAfterAuth(request, response));
    }





    // close connection to database on destroy
    public void destroy(){
        postService.close();
        System.out.println("Destroyed");
    }
}

