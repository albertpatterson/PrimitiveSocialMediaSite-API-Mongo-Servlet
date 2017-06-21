package com.primitive_social_media.sessions;

import com.primitive_social_media.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by apatters on 6/12/2017.
 */
@WebServlet(name = "SessionServlet")
public class SessionServlet extends HttpServlet {

    private SessionService sessionService = new SessionService();

    public void init(){
        sessionService.connect();
    }


    /**
     *
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
     *
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
     *
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

    public void destroy(){
        sessionService.close();
    }

}
