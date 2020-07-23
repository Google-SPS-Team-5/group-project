package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.User;
import static com.google.sps.Constants.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns a specific user */
@WebServlet("/userinformation")
public class UserServlet extends HttpServlet {

  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  Gson gson = new Gson();
  Query query = new Query("User");
  
  /** Writes a JSON-ified of a specific user from the Datastore. The key is set to a default data as of now
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    //dummy data for user
    String authenticatedEmail = "sam@gmail.com";

    

    
    
    String userJson = "";

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    for(Entity entity : results.asIterable()){
      String email = (String) entity.getProperty(USER_EMAIL);
      if(email.equals(authenticatedEmail)){
        String name = (String) entity.getProperty(USER_NAME);
        String[] favouritesArr = gson.fromJson((String) entity.getProperty(USER_FAVOURITES), String[].class);
        List<String>favourites = Arrays.asList(favouritesArr);

        User user = new User(name, email, favourites);
        userJson = String.format(gson.toJson(user));
      }
    }

    response.setContentType("application/json;");
    response.getWriter().println(userJson);
  }

  /** Retrieve user's data from form and inserting into Datastore
  */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
    Gson gson = new Gson();

    //Get input from form
    String name = request.getParameter(USER_NAME);
    String email = request.getParameter(USER_EMAIL);
    List<String> favourites = Arrays.asList();

    Entity userEntity = new Entity("User");
    userEntity.setProperty(USER_NAME, name);
    userEntity.setProperty(USER_EMAIL, email);
    userEntity.setProperty(USER_FAVOURITES, gson.toJson(favourites));

    
    datastore.put(userEntity);

    response.sendRedirect("/index.html");
  }
}