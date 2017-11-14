package com.primitive_social_media.personalData;

import com.primitive_social_media.JSONConvertible;
import com.primitive_social_media.PersonalData;
import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.exception.InvalidDataException;
import com.primitive_social_media.exception.NullParameterException;
import com.primitive_social_media.exception.ServiceException;
import com.primitive_social_media.session.SessionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc service requests for personal data of users
 */
@WebServlet(name = "PersonalDataServlet")
public class PersonalDataServlet extends HttpServlet {

    private DatabaseService databaseService = MockDatabaseService.getInstance();
    private SessionService sessionService = new SessionService();

    /**
     * connect to the database on creation
     */
    public void init(){
        databaseService.connect();
    }

    /**
     * service request to create  a new user
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = NullParameterException.assertParameter(request, "username");
            String password = NullParameterException.assertParameter(request, "password");
            String location = NullParameterException.assertParameter(request, "location");
            String DOBString = NullParameterException.assertParameter(request,"DOB");
            String business = NullParameterException.assertParameter(request, "business");
            String picture = NullParameterException.assertParameter(request, "picture");

            if (databaseService.checkUser(username)) {
                String msg = String.format("Username \"%s\" already in use.", username);
                throw new InvalidDataException(msg);
            } else {
                PersonalData userData = new PersonalData(username, location, DOBString, business, picture);
                databaseService.addUser(username, userData, password);
                response.setStatus(HttpServletResponse.SC_CREATED);
            }
        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }


    /**
     * service request to get the data of an existing user
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            sessionService.assertSession(request);

            String responseJSON;
            String desiredUserName = request.getParameter("desiredUserName");
            if(desiredUserName != null){
                PersonalData data = databaseService.findPersonalData(desiredUserName);
                responseJSON = data.toJSON();
            }else{
                String desiredUserQuery = NullParameterException.assertParameter(request,"desiredUserQuery");
                HashMap<String, PersonalData> matches = databaseService.findPersonalDataMultiple(desiredUserQuery);
                ArrayList<PersonalData> data = new ArrayList<>(matches.size());
                for(Map.Entry<String, PersonalData> match : matches.entrySet()){
                    data.add(match.getValue());
                }
                responseJSON = JSONConvertible.toJSONList(data);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(responseJSON);

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }

    /**
     * service request to update data of an existing user
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            sessionService.assertSession(request);

            String username = NullParameterException.assertParameter(request,"username");
            String location = request.getParameter("location");
            String DOBString = request.getParameter("DOB");
            String business = request.getParameter("business");
            String picture = request.getParameter("picture");

            PersonalData personalData = databaseService.findPersonalData(username);
            if (location != null) personalData.location = location;
            if (business != null) business = personalData.business;
            if (picture != null) picture = personalData.picture;
            try {
                if (DOBString != null) personalData.setDOBString(DOBString);
            }catch(ParseException parseException){
                String msg = String.format("Invalid data: \"%s\" format must be DD/MM/YYY", DOBString);
                throw new InvalidDataException(msg);
            }

            databaseService.setPersonalData(username, personalData);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

            System.out.println("updated user " + username);
        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }

    /**
     * service request to delete an existing user
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            sessionService.assertSession(request);

            String username = NullParameterException.assertParameter(request,"username");

            databaseService.deleteUser(username);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

            System.out.println("Deleted user " + username);

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }

    /**
     * close the connection on destruction
     */
    public void destroy(){
        databaseService.close();
    }
}
