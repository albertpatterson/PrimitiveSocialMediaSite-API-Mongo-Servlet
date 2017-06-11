package com.primative_social_medial.posts;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by apatters on 6/11/2017.
 */
@WebServlet(name = "Servlet")
public class PostsServlet extends HttpServlet {

    public MockPostManager postManager = new MockPostManager();

    // connect to database on init
    public void init(){
        postManager.connect();
        System.out.println("Initialized");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        String poster = request.getParameter("poster");
        String content = request.getParameter("content");

        postManager.addPost(poster, content);

        PrintWriter out = response.getWriter();
        out.println("Success");
        out.flush();

        System.out.println("Created a Post");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.print(postManager.getPosts());
        out.flush();

        System.out.println("Sent Posts");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        int index = Integer.parseInt(request.getParameter("index"));
        postManager.deletePost(index);

        PrintWriter out = response.getWriter();
        out.println("Success");
        out.flush();

        System.out.println("Deleted a Post");
    }


    // close connection to database on destroy
    public void destroy(){
        postManager.close();
        System.out.println("Destroyed");
    }
}

