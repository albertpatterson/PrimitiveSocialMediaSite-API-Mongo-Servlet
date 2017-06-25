package com.primitive_social_media.exception;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by apatters on 6/21/2017.
 */
public class NullParameterException extends ServiceException{

    public static String assertParameter(HttpServletRequest request, String parameter) throws NullParameterException {
        String value = request.getParameter(parameter);
        if(value == null ){
            throw new NullParameterException(parameter);
        }
        return value;
    }

    private String parameter;

    public NullParameterException(String parameter){
        this.parameter = parameter;
    }

    @Override
    public String getMessage() {
        return String.format("parameter \"%s\" is required", parameter);
    }
}
