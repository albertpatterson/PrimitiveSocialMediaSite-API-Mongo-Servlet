package com.primitive_social_media.signUp;

import com.primitive_social_media.PersonalData;
import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.exception.NullParameterException;
import com.primitive_social_media.exception.ServiceException;
import com.primitive_social_media.session.SessionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * @desc Service requests to sign up for the app
 */
@WebServlet(name = "SignUpServlet")
public class SignUpServlet extends HttpServlet {

    private SessionService sessionService = new SessionService();
    private DatabaseService databaseService = MockDatabaseService.getInstance();

    /**
     * connect to the database
     */
    public void init(){
        databaseService.connect();
    }

    /**
     * service request to create a new user
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{

            String username = NullParameterException.assertParameter(request, "username");
            String location = NullParameterException.assertParameter(request, "location");
            String DOB = NullParameterException.assertParameter(request, "DOB");
            String business = NullParameterException.assertParameter(request, "business");
            String password = NullParameterException.assertParameter(request, "password");

            FileOutputStream fop = null;
            try {
                Part filePart = request.getPart("picture"); // Retrieves <input type="file" name="file">
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
                InputStream fileContent = filePart.getInputStream();

                String extension = "";

                int i = fileName.lastIndexOf('.');
                if (i > 0) {
                    extension = fileName.substring(i+1);
                }

                String pictureFilename = username+"."+extension;
                File file = new File("C:\\Users\\apatters\\OneDrive_personal\\OneDrive\\code\\java\\psm\\api\\PrimitiveSocialMediaSite-API-Mongo-Servlet\\web\\static\\" + pictureFilename);
                fop = new FileOutputStream(file);


                byte[] buffer = new byte[1024];
                int len = fileContent.read(buffer);
                while (len != -1) {
                    fop.write(buffer, 0, len);
                    len = fileContent.read(buffer);
                }
                fop.flush();

                PersonalData personalData = new PersonalData(username, location, DOB, business, "static/"+pictureFilename);
                databaseService.addUser(username, personalData, password);

                response.setStatus(HttpServletResponse.SC_CREATED);
                System.out.println("Created a user");

            }finally {
                if (fop != null) {
                    fop.close();
                }
            }


        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }

    }

    /**
     * close the connection to the database
     */
    public void destroy(){
        databaseService.close();
        System.out.println("Destroyed");
    }
}
