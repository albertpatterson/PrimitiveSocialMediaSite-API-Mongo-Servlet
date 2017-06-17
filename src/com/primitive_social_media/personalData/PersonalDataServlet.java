package com.primitive_social_media.personalData;

import com.primitive_social_media.PersonalData;
import com.primitive_social_media.User;
import com.primitive_social_media.database.DatabaseService;
import com.primitive_social_media.database.MockDatabaseService;
import com.primitive_social_media.sessions.SessionService;

import javax.print.attribute.PrintRequestAttribute;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

/**
 * Created by apatters on 6/11/2017.
 */
@WebServlet(name = "PersonalDataServlet")
public class PersonalDataServlet extends HttpServlet {

    private DatabaseService databaseService = new MockDatabaseService();
    private SessionService sessionService = new SessionService();

    public void init(){
        System.out.println("Initialized UserServlet");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String location = request.getParameter("location");
        String DOBString = request.getParameter("DOB");
        String business = request.getParameter("business");
        String picture = request.getParameter("picture");

        if(databaseService.checkUser(username)){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            String msg = String.format("Username \"%s\" already in use.", username);
            out.print(msg);
        }else {
            PersonalData userData = new PersonalData(username, location, DOBString, business, picture);
            databaseService.addUser(username, userData, password);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }
    }





    protected void getPersonalDataAfterAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");

        try {
            PersonalData data = databaseService.findPersonalData(username);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(data.toJSON());
        } catch(Error e){
            System.out.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionService.validateThenRespond(request, response, ()->getPersonalDataAfterAuth(request, response));
    }

    protected void putPersonalDataAfterAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String username = request.getParameter("username");
            String location = request.getParameter("location");
            String DOBString = request.getParameter("DOB");
            String business = request.getParameter("business");
            String picture = request.getParameter("picture");

            PersonalData personalData = databaseService.findPersonalData(username);
            if (location != null) personalData.location = location;
            if (DOBString != null) personalData.setDOBString(DOBString);
            if (business != null) business = personalData.business;
            if (picture != null) picture = personalData.picture;

            databaseService.setPersonalData(username, personalData);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

            System.out.println("updated user " + username);
        } catch (ParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionService.validateThenRespond(request, response, ()->putPersonalDataAfterAuth(request, response));
    }


    protected void deleteUserAfterAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");

        databaseService.deleteUser(username);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);

        System.out.println("Deleted user " + username);
    };
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        sessionService.validateThenRespond(request, response, ()->deleteUserAfterAuth(request, response));
    }


    public void destroy(){

//        databaseService.close();
        System.out.println("destroyed UserServlet");
    }
}
