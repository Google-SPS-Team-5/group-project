
package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.Gson;
import static com.google.sps.Constants.*;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** TODO: Handle checking of business ID validity, and what happens to business ownership when a business is deleted. **/
/** Servlet that assigns business ownership to users. */
@WebServlet("/assign-business-ownership")
public class BusinessOwnershipServlet extends HttpServlet {

  Gson gson = new Gson();
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  /** Gets input from assignOwner form and assigns business ID to user. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      // Get input from the form
      String businessID = request.getParameter(BUSINESS_ID);
      String userID = request.getParameter(USER_EMAIL);
      Key userKey = KeyFactory.createKey("User", userID);
      Entity userEntity = datastore.get(userKey);
      
      // Assign business ownership property.
      userEntity.setProperty(USER_BUSINESS_OWNERSHIP, businessID);
      datastore.put(userEntity);

      // Redirect back to the main page.
      response.sendRedirect("/index.html");

    } catch (IOException err) {
      System.out.println(err);
    } catch (EntityNotFoundException err) {
      System.out.println(err);
    } 
  }

}