package com.primitive_social_media.users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by apatters on 6/11/2017.
 */
@WebServlet(name = "UsersServletjd")
public class UsersServlet extends HttpServlet {

    private MockUserManager userManager = new MockUserManager();

    public void init(){

        userManager.connect();

        System.out.println("Initialized UserServlet");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPut(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");

        String usersJSON = User.toJSON(userManager.getUsers(query));

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(usersJSON);
        out.flush();

        System.out.println("Sent users matching " + query);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String location = request.getParameter("location");
        String DOBString = request.getParameter("DOB");
        String business = request.getParameter("business");
        String picture = request.getParameter("picture");

        User newUser = new User(name, password, location, DOBString, business, picture);
        userManager.putUser(newUser);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print("success");
        out.flush();

        System.out.println("Put user " + name);
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        userManager.deleteUser(name);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        out.print("success");
        out.flush();

        System.out.println("Deleted user " + name);
    }

    public void destroy(){

        userManager.close();
        System.out.println("destroyed UserServlet");
    }
}
