package com.primitive_social_media.post;

import com.primitive_social_media.JSONConvertible;
import com.primitive_social_media.Post;
import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.exception.InvalidDataException;
import com.primitive_social_media.exception.NullParameterException;
import com.primitive_social_media.exception.ServiceException;
import com.primitive_social_media.session.SessionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @desc Service requests for posts
 */
@WebServlet(name = "PersonalDataServlet")
public class PostServlet extends HttpServlet {

    private SessionService sessionService = new SessionService();
    private DatabaseService databaseService = MockDatabaseService.getInstance();

    /**
     * connect to the database on creation
     */
    public void init(){
        databaseService.connect();
    }

    /**
     * service request to create a new post
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            sessionService.assertSession(request);

            String poster = NullParameterException.assertParameter(request,"username");
            String content = NullParameterException.assertParameter(request,"content");

            databaseService.addPost(poster, new Post(poster, content));

            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_CREATED);

            System.out.println("Created a Post");

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }

    /**
     * service request to get existing posts
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            sessionService.assertSession(request);

            String postType = NullParameterException.assertParameter(request,"type");
            ArrayList<Post> posts;

            switch(postType) {
                case "followed":
                    String username = request.getParameter("username");
                    posts = databaseService.getFollowedPosts(username);
                    break;
                case "own":
                    String poster = NullParameterException.assertParameter(request,"poster");
                    posts = databaseService.getOwnPosts(poster);
                    break;
                default:
                    String msg = String.format("Invalid post type requested: %s", postType);
                    System.out.println(msg);
                    throw new InvalidDataException(msg);
            }

            String JSON = JSONConvertible.toJSONList(posts);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(JSON);
            out.flush();
            System.out.println("Sent Posts");

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }

    /**
     * service request to delete existing post
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            sessionService.assertSession(request);

            String username = NullParameterException.assertParameter(request,"username");
            String indexStr = NullParameterException.assertParameter(request,"index");
            try {
                int index = Integer.parseInt(indexStr);

                databaseService.deletePost(username, index);

                response.setStatus(HttpServletResponse.SC_NO_CONTENT);

                System.out.println("Deleted a Post");
            }catch(NumberFormatException numberFormatException){
                String msg = String.format("invalid index \"%s\"", indexStr);
                throw new InvalidDataException(msg);
            }

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }


    /**
     * close the connection to the database on destruction
     */
    public void destroy(){
        databaseService.close();
        System.out.println("Destroyed");
    }
}

