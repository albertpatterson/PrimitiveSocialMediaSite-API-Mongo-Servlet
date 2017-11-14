package com.primitive_social_media.exception;

import javax.servlet.http.HttpServletResponse;

/**
 * @desc Exception representing an invalid user session
 */
public class SessionNotValidException extends ServiceException {

    public SessionNotValidException(){
        responseStatus = HttpServletResponse.SC_UNAUTHORIZED;
    }

    @Override
    public String getMessage(){
        return "Session is not valid. Please sign in";
    }
}
