package com.primitive_social_media.session;

import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.exception.NullParameterException;
import com.primitive_social_media.exception.SessionNotValidException;
import com.primitive_social_media.exception.UserNotExistsException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by apatters on 6/12/2017.
 */
public class SessionService {

    private DatabaseService databaseService = new MockDatabaseService();

    public void connect(){
        databaseService.connect();
    }

    /**
     *
     * @param request
     * @return
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

    public void assertSession(HttpServletRequest request) throws SessionNotValidException, NullParameterException {
        String username = NullParameterException.assertParameter(request,"username");
        HttpSession session = request.getSession();
        Object sessionUsername = session.getAttribute("username");
        if((sessionUsername==null)||(!sessionUsername.toString().equals(username))){
            throw new SessionNotValidException();
        }
    }


    /**
     *
      * @param request
     */
    public void deleteSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }



    public void close(){

    }
}
