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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        if(sessionService.signIn(request)){
            out.println("success");
            System.out.println("Signed In");
        }else{
            out.println("Invalid username and/or password");
            System.out.println("Failed to Signed In");
        }

        out.flush();
    }

    protected SessionService.ValidationResultHandler getSession = (HttpServletRequest request, HttpServletResponse response)-> {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("success");
        out.flush();

        System.out.println("valid session");
    };

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionService.validateThenRespond(request, response, getSession);
    }

    protected SessionService.ValidationResultHandler deleteSession = (HttpServletRequest request, HttpServletResponse response)-> {
        sessionService.deleteSession(request);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.println("success");
        out.flush();

        System.out.println("Deleted session");
    };

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionService.validateThenRespond(request, response, deleteSession);
    }

    public void destroy(){
        sessionService.close();
    }

}
