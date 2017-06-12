package com.primitive_social_media.sessions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * Created by apatters on 6/12/2017.
 */
public class SessionService {

    public void connect(){

    }

    public boolean signIn(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // check password in db

        // create session

        // return result
        boolean check = username.equals(password);
        if(check){
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
        }
        return check;
    }

    public boolean validateSession(HttpServletRequest request){
        String username = request.getParameter("username");
        HttpSession session = request.getSession();
        Object sessionUsername = session.getAttribute("username");
        return !(sessionUsername==null)&&(sessionUsername.toString().equals(username));
    }

    public void deleteSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }

    public void close(){

    }

    public interface ValidationResultHandler{
        void respond() throws IOException;
    }

    public void validateThenRespond(HttpServletRequest request, ValidationResultHandler successHandler, HttpServletResponse response) throws IOException {
        validateThenRespond(request, successHandler, ()->defaultValidationFailure(response));
    }

    private void defaultValidationFailure(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    public void validateThenRespond(HttpServletRequest request, ValidationResultHandler validationSuccessHandler, ValidationResultHandler validationFailureHandler) throws IOException{
        if(validateSession(request)){
            validationSuccessHandler.respond();
        }else{
            validationFailureHandler.respond();
        }
    }
}
