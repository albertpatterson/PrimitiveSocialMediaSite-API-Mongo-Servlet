package com.primitive_social_media.session;

import com.primitive_social_media.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Service requests for session data
 */
@WebServlet(name = "SessionServlet")
public class SessionServlet extends HttpServlet {

    private SessionService sessionService = new SessionService();

    /**
     * connect to the sessoin service
     */
    public void init(){
        sessionService.connect();
    }


    /**
     * service request to create a new session (sign in)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            if (sessionService.signIn(request)) {
                response.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }


    /**
     * service request to check for an existing session
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            sessionService.assertSession(request);

            response.setStatus(HttpServletResponse.SC_OK);

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }


    /**
     * service request to delete an existing session (sign out)
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            sessionService.assertSession(request);

            sessionService.deleteSession(request);

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

            System.out.println("Deleted session");

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }

    /**
     * close connection
     */
    public void destroy(){
        sessionService.close();
    }

}
