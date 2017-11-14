package com.primitive_social_media.premium;

import com.primitive_social_media.JSONConvertible;
import com.primitive_social_media.Post;
import com.primitive_social_media.PremiumContent;
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
 * @desc Service requests for premium content
 */
@WebServlet(name = "PremiumServlet")
public class PremiumServlet extends HttpServlet {
    private SessionService sessionService = new SessionService();
    private DatabaseService databaseService = MockDatabaseService.getInstance();

    // connect to database on init
    public void init(){
        databaseService.connect();
    }

    /**
     * service request to create a new premium item
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            sessionService.assertSession(request);

            String username = NullParameterException.assertParameter(request,"username");
            String content = NullParameterException.assertParameter(request,"content");

            databaseService.addPremium(username, new PremiumContent(content));

            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_CREATED);

            System.out.println("Created a Premium item");

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }

    /**
     * service request to get existing premium items
     * @param request
     * @param response
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            sessionService.assertSession(request);

            String username = NullParameterException.assertParameter(request,"username");

            ArrayList<PremiumContent> premiumContent = databaseService.getPremium(username);
            String JSON = JSONConvertible.toJSONList(premiumContent);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(JSON);

            System.out.println("Sent Premium items");
        } catch (ServiceException e) {
            e.respond(response);
        }
    }

    /**
     * service request to delete existing premium item
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            sessionService.assertSession(request);

            String username = NullParameterException.assertParameter(request,"username");
            String indexStr = NullParameterException.assertParameter(request,"index");
            try {
                int index = Integer.parseInt(indexStr);

                databaseService.deletePremium(username, index);

                response.setStatus(HttpServletResponse.SC_NO_CONTENT);

                System.out.println("Deleted a Premium item");
            }catch(NumberFormatException numberFormatException){
                String msg = String.format("invalid index \"%s\"", indexStr);
                throw new InvalidDataException(msg);
            }

        }catch(ServiceException serviceException){
            serviceException.respond(response);
        }
    }


    /**
     * close the connection to the database on destruction
     */
    public void destroy(){
        databaseService.close();
    }
}
