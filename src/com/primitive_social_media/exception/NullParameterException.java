package com.primitive_social_media.exception;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc Exception representing missing mandatory parameter in a request
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
