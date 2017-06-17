package com.primitive_social_media.sessions;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(sessionService.signIn(request)){
            response.setStatus(HttpServletResponse.SC_CREATED);
        }else{
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }





    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    protected void getSessionAfterAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
    };

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("here");

        sessionService.validateThenRespond(request, response, ()->getSessionAfterAuth(request, response));
    }






    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    protected void deleteSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        sessionService.deleteSession(request);

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        System.out.println("Deleted session");
    };

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionService.validateThenRespond(request, response, ()->deleteSession(request, response));
    }

    public void destroy(){
        sessionService.close();
    }

}
