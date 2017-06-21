package com.primitive_social_media.exception;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by apatters on 6/21/2017.
 */
public abstract class ServiceException extends Exception {

    public int responseStatus;

    public void respond(HttpServletResponse response) throws IOException {
        response.setStatus(responseStatus);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String msg = getMessage();
        out.print(msg);
        System.out.println(msg);
    }

    public abstract String getMessage();
}
