package com.primitive_social_media.images;

import com.primitive_social_media.exception.ServiceException;
import com.primitive_social_media.session.SessionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

/**
 * @desc Service requests related to images
 */
@WebServlet(name = "ImageServlet")
public class ImageServlet extends HttpServlet {

    private SessionService sessionService = new SessionService();

// todo: implement image post
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }

    /**
     * service request for an image
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

            String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
            File file = new File("C:\\Users\\apatters\\OneDrive_personal\\OneDrive\\code\\java\\psm\\api\\PrimitiveSocialMediaSite-API-Mongo-Servlet\\web\\static\\", filename);
            response.setHeader("Content-Type", getServletContext().getMimeType(filename));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            Files.copy(file.toPath(), response.getOutputStream());
    }
}
