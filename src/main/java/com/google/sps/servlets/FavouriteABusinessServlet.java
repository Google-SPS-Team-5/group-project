package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import static com.google.sps.Constants.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/favourite-a-business")
public class FavouriteABusinessServlet extends HttpServlet {
  Gson gson = new Gson();
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException  {
    
    try {
      
      String businessID = request.getParameter(BUSINESS_ID); 
      String email = request.getParameter(USER_EMAIL);
      boolean userDidFavourite = request.getParameter(USER_DID_FAVOURITE) == null ? false : true;
      Key userKey = KeyFactory.createKey("User", email);
      Entity userEntity = getUserEntity(userKey); //retrieve by user's key
      String[] favouritesArr = gson.fromJson((String) userEntity.getProperty(USER_FAVOURITES), String[].class);
      List<String> favouritesList = new ArrayList<String>(Arrays.asList(favouritesArr));

      Set<String> uniqueBusinessesList;

      if (favouritesArr != null) {
        uniqueBusinessesList = new HashSet<String>(favouritesList);
      } else {
        uniqueBusinessesList = new HashSet<String>();
      }
      
      if (userDidFavourite) {
        uniqueBusinessesList.add(businessID);
      } else {
        uniqueBusinessesList.remove(businessID);
      }

      favouritesList = new ArrayList<String>(uniqueBusinessesList);
      
      userEntity.setProperty(USER_FAVOURITES, gson.toJson(favouritesList));
      datastore.put(userEntity);
    } catch (EntityNotFoundException err) {
      System.out.println(err);
    } catch (Exception err) {
      System.out.println(err);
    }

    response.sendRedirect(request.getHeader("referer"));
  }

  private Entity getUserEntity(Key userKey) throws EntityNotFoundException {
    Entity userEntity = datastore.get(userKey);
    return userEntity;
  }
}