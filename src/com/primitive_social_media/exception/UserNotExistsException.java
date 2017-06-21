package com.primitive_social_media.exception;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by apatters on 6/20/2017.
 */
public class UserNotExistsException extends ServiceException {

    private String username;

    public UserNotExistsException(String username){
        this.username = username;
        responseStatus = HttpServletResponse.SC_NOT_FOUND;
    }

    public String getMessage(){
        return String.format("User \"%s\" does not exist.", username);
    }
}
