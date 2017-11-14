package com.primitive_social_media.post.subscription;

import com.primitive_social_media.JSONConvertible;
import com.primitive_social_media.Post;
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
import java.util.ArrayList;

/**
 * @desc service requests for subcriptions
 */
@WebServlet(name = "PersonalDataServlet")
public class SubscriptionServlet extends HttpServlet {

    private SessionService sessionService = new SessionService();
    private DatabaseService databaseService = MockDatabaseService.getInstance();

    // connect to database on init
    public void init(){
        databaseService.connect();
    }

    /**
     * service request to create a new subscription
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            sessionService.assertSession(request);

            String username = NullParameterException.assertParameter(request,"username");
            String followee = NullParameterException.assertParameter(request,"followee");

            databaseService.addSubscription(username, followee);

            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_CREATED);

            System.out.println("Created a subscription");

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }

    /**
     * service request to get existing subscriptions
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            sessionService.assertSession(request);

            String username = NullParameterException.assertParameter(request,"username");

            ArrayList<String> subscriptions = databaseService.getSubscriptions(username);

            String JSON = JSONConvertible.toJSONListFromStrings(subscriptions);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(JSON);
            out.flush();
            System.out.println("Sent Posts");

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }

    /**
     * service requests to delete an existing subscription
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            sessionService.assertSession(request);

            String username = NullParameterException.assertParameter(request,"username");
            String followee = NullParameterException.assertParameter(request,"followee");

            databaseService.deleteSubscription(username, followee);

            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

            System.out.println("deleted a subscription");
        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }


    // close connection to database on destroy
    public void destroy(){
        databaseService.close();
    }
}

