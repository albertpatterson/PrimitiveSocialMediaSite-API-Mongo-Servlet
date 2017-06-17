package com.primitive_social_media.sessions;

import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by apatters on 6/12/2017.
 */
public class SessionService {

    private DatabaseService databaseService = new MockDatabaseService();

    public void connect(){
        databaseService.connect();
    }

    public boolean signIn(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // check password in db

        // create session

        // return result
        boolean check = databaseService.getPassword(username).equals(password);

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
//        void respond(HttpServletRequest request, HttpServletResponse response) throws IOException;
        void respond() throws IOException;
    }
    private void defaultValidationFailureHandler(HttpServletRequest request, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    };
//    private ValidationResultHandler defaultValidationFailureHandler = (request, response)->{
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//    };

    public void validateThenRespond(HttpServletRequest request,
                                    HttpServletResponse response,
                                    ValidationResultHandler successHandler) throws IOException {


        validateThenRespond(request, response, successHandler, ()->defaultValidationFailureHandler(request, response));
    }

//    private void defaultValidationFailure(HttpServletResponse response) throws IOException {
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//    }

    public void validateThenRespond(HttpServletRequest request, HttpServletResponse response,
                                    ValidationResultHandler validationSuccessHandler,
                                    ValidationResultHandler validationFailureHandler) throws IOException{
        if(validateSession(request)){
            validationSuccessHandler.respond();
        }else{
            validationFailureHandler.respond();
        }
//        if(validateSession(request)){
//            validationSuccessHandler.respond(request, response);
//        }else{
//            validationFailureHandler.respond(request, response);
//        }
    }
}
