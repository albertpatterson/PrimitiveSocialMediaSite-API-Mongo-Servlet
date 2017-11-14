package com.primitive_social_media.exception;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

/**
 * @desc Exception representing an attempt to access data of a non existent user
 */
public class UserNotExistsException extends ServiceException {

    private String username;

    public UserNotExistsException(String username){
        this.username = username;
        responseStatus = HttpServletResponse.SC_NOT_FOUND;
    }

    @Override
    public String getMessage(){
        return String.format("User \\\"%s\\\" does not exist.", username);
    }
}
