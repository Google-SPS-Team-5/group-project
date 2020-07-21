package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
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
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/favourite-a-business")
public class FavouriteABusinessServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
    try{
      Gson gson = new Gson();

      String businessID = request.getParameter(businessID); 
      String email = request.getParameter(USER_EMAIL);

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

      Key userKey = KeyFactory.stringToKey(email);
      Entity userEntity = datastore.get(userKey); //retrieve by user's key
      String[] favouritesArr = gson.fromJson((String) userEntity.getProperty(USER_FAVOURITES), String[].class);
      List<String> favouritesList;

      if (favouritesArr != null) {
        favouritesList = new ArrayList<String>(Arrays.asList(favouritesArr));
      } else {
        favouritesList = new ArrayList<String>();
      }
      
      favouritesList.add(businessID);
      userEntity.setProperty(USER_FAVOURITES, gson.toJson(favouritesList));
    } catch (IOException err) {
        System.out.println(err);
    } catch (EntityNotFoundException err) {
        System.out.println(err);
    }
  }
}