package com.primitive_social_media.exception;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by apatters on 6/21/2017.
 */
public class SessionNotValidException extends ServiceException {


    public SessionNotValidException(){
        responseStatus = HttpServletResponse.SC_UNAUTHORIZED;
    }

    public String getMessage(){
        return "Session is not valid. Please sign in";
    }
}
