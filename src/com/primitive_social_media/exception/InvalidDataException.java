package com.primitive_social_media.exception;

import com.sun.deploy.net.HttpResponse;
import org.omg.CORBA.DynAnyPackage.Invalid;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by apatters on 6/21/2017.
 */
public class InvalidDataException extends ServiceException {

    private String msg;


    public InvalidDataException(String msg){
        this.msg = msg;
        this.responseStatus = HttpServletResponse.SC_BAD_REQUEST;
    }

    public String getMessage(){
        return msg;
    }
}
