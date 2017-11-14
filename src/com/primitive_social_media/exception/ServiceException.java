package com.primitive_social_media.exception;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @desc Exception representing an issue servicing a request
 */
public abstract class ServiceException extends Exception {

    public int responseStatus;

    /**
     * respond to the request with information about the Exception that occurred
     * @param response - the response object corresponding to the request
     * @throws IOException
     */
    public void respond(HttpServletResponse response) throws IOException {
        response.setStatus(responseStatus);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String msg = getMessage();
        out.print("{" +
                "\"message\": \"" + msg + "\"," +
                "\"status\": " + responseStatus +
                "}");
        out.flush();

        System.out.println(msg);
    }

    public abstract String getMessage();
}
