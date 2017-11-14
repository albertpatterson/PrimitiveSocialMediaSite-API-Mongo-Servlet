package com.primitive_social_media.session;

import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.exception.NullParameterException;
import com.primitive_social_media.exception.SessionNotValidException;
import com.primitive_social_media.exception.UserNotExistsException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @desc Service for managing user session data
 */
public class SessionService {

    private DatabaseService databaseService = MockDatabaseService.getInstance();

    /**
     * connect to the database
     */
    public void connect(){
        databaseService.connect();
    }

    /**
     * sign a user into the app
     * @param request the sign in request
     * @return boolean indicating if sign in was successful
     */
    public boolean signIn(HttpServletRequest request) throws UserNotExistsException, NullParameterException {
        String username = NullParameterException.assertParameter(request,"username");
        String password = NullParameterException.assertParameter(request,"password");

        boolean check = databaseService.getPassword(username).equals(password);

        if(check){
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
        }
        return check;
    }

    /**
     * assert that the user has a valid session
     * @param request - the request sent by the user
     * @throws SessionNotValidException
     * @throws NullParameterException
     */
    public void assertSession(HttpServletRequest request) throws SessionNotValidException, NullParameterException {

        if(!request.isRequestedSessionIdValid()){
            throw new SessionNotValidException();
        }

        String username = NullParameterException.assertParameter(request,"username");
        HttpSession session = request.getSession();
        Object sessionUsername = session.getAttribute("username");
        if((sessionUsername==null)||(!sessionUsername.toString().equals(username))){
            throw new SessionNotValidException();
        }
    }


    /**
     * delete an existing session (sign out)
     * @param request the sign out request
     */
    public void deleteSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }


    /**
     * close the connection to the database
     */
    public void close(){
        databaseService.connect();
    }
}
