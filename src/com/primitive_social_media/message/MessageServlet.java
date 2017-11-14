package com.primitive_social_media.message;

import com.primitive_social_media.JSONConvertible;
import com.primitive_social_media.Post;
import com.primitive_social_media.exception.InvalidDataException;
import com.primitive_social_media.exception.NullParameterException;
import com.primitive_social_media.exception.ServiceException;
import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.session.SessionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @desc Service requests related to messages
 */
public class MessageServlet extends HttpServlet {

    private SessionService sessionService = new SessionService();
    private DatabaseService databaseService = MockDatabaseService.getInstance();

    /**
     * connect to the database on creation
     */
    public void init(){
        databaseService.connect();
    }

    /**
     * service post request to create a new message
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try{
            sessionService.assertSession(request);

            String poster = NullParameterException.assertParameter(request, "username");
            String content = NullParameterException.assertParameter(request, "content");
            String recipient = NullParameterException.assertParameter(request, "recipient");

            databaseService.addMessage(recipient, new Post(poster, content));

            response.setStatus(HttpServletResponse.SC_CREATED);
            System.out.println("Created a Message");

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }


    /**
     * service request for existing messages
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            sessionService.assertSession(request);

            String username = NullParameterException.assertParameter(request,"username");

            ArrayList<Post> messages = databaseService.getMessages(username);
            String JSON = JSONConvertible.toJSONList(messages);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(JSON);

            System.out.println("Sent Messages");
        } catch (ServiceException e) {
            e.respond(response);
        }
    }

    /**
     * service request to delete existing message
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

                databaseService.deleteMessage(username, index);

                response.setStatus(HttpServletResponse.SC_NO_CONTENT);

                System.out.println("Deleted a Post");
            }catch(NumberFormatException numberFormatException){
                String msg = String.format("invalid index \"%s\"", indexStr);
                throw new InvalidDataException(msg);
            }

        } catch (ServiceException e) {
            e.respond(response);
        }
    }


    /**
     * close the connection to the database when destroyed
     */
    public void destroy(){
        databaseService.close();
    }
}
