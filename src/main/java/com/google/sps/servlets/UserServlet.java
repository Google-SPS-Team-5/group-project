package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.sps.User;
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

  /** Writes a JSON-ified of a specific user from the Datastore. The key is set to a default data as of now
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //dummy data for user
    String authenticatedEmail = "sam@gmail.com";

    Gson gson = new Gson();

    Query query = new Query("User");
    
    String userJson = "";

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    for(Entity entity : results.asIterable()){
      String email = (String) entity.getProperty("email");
      if(email.equals(authenticatedEmail)){
        String name = (String) entity.getProperty("name");
        String[] favouritesArr = gson.fromJson((String) entity.getProperty("favourites"), String[].class);
        List<String> favourites = Arrays.asList(favouritesArr);
        String[] pastReviewsArr = gson.fromJson((String) entity.getProperty("pastReviews"), String[].class);
        List<String> pastReviews = Arrays.asList(pastReviewsArr);

        User user = new User(name, email, favourites, pastReviews);
        userJson = String.format(gson.toJson(user));
      }
    }

    response.setContentType("application/json;");
    response.getWriter().println(userJson);
  }

  /** Inserting hardcoded user data into the Datastore
  */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
    // Gson gson = new Gson();

    // List<String> favourites = Arrays.asList("1","2","3","4");
    // List<String> pastReviews = Arrays.asList("5","6","7","8");

    // Entity user1 = new Entity("User");
    // user1.setProperty("name", "Sam");
    // user1.setProperty("email", "sam@gmail.com");
    // user1.setProperty("favourites", gson.toJson(favourites));
    // user1.setProperty("pastReviews", gson.toJson(pastReviews));

    // Entity user2 = new Entity("User");
    // user2.setProperty("name", "Wendy");
    // user2.setProperty("email", "wendy@gmail.com");
    // user2.setProperty("favourites", gson.toJson(favourites));
    // user2.setProperty("pastReviews", gson.toJson(pastReviews));

    // Entity user3 = new Entity("User");
    // user3.setProperty("name", "Tiffany");
    // user3.setProperty("email", "tiffany@gmail.com");
    // user3.setProperty("favourites", gson.toJson(favourites));
    // user3.setProperty("pastReviews", gson.toJson(pastReviews));

    // DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    // datastore.put(user1);
    // datastore.put(user2);
    // datastore.put(user3);
  }
}