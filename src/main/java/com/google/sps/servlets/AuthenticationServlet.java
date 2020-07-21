package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.gson.Gson;
import static com.google.sps.Constants.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet {

  Gson gson = new Gson();
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  private final String USER_JSON_DETAILS = "{ \"userEmail\": \"%s\", \"url\": \"%s\", \"isAdmin\": \"%s\", \"username\": \"%s\", \"favourites\": \"%s\" }";
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");
    UserService userService = UserServiceFactory.getUserService();

    if (userService.isUserLoggedIn()) {
      String userEmail = userService.getCurrentUser().getEmail();
      String urlToRedirectToAfterUserLogsOut = request.getHeader("referer");
      if (urlToRedirectToAfterUserLogsOut == "" || urlToRedirectToAfterUserLogsOut == null){
        urlToRedirectToAfterUserLogsOut = "/";
      }
      String username = userService.getCurrentUser().getNickname();
      Boolean isAdmin = userService.isUserAdmin();
      String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);

      Entity user = CheckForUserProfile(userEmail, username);
      String json = String.format(USER_JSON_DETAILS, userEmail, logoutUrl, isAdmin, username, Arrays.asList(gson.fromJson((String)user.getProperty(USER_FAVOURITES), String[].class)));
      
      response.getWriter().println(json);
    } else {
      String urlToRedirectToAfterUserLogsIn = request.getHeader("referer");
      if (urlToRedirectToAfterUserLogsIn == "" || urlToRedirectToAfterUserLogsIn == null){
        urlToRedirectToAfterUserLogsIn = "/";
      }
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);

      String json = String.format(USER_JSON_DETAILS, "", loginUrl, "", "", "");
      response.getWriter().println(json);
    }
  }

  private Entity CheckForUserProfile(String userEmail, String username) {
    Entity userEntity;
    try {
      userEntity = getUserEntity(userEmail);
    } catch (EntityNotFoundException err){
      userEntity = new Entity("User", userEmail);
      userEntity.setProperty(USER_NAME, username);
      userEntity.setProperty(USER_FAVOURITES, gson.toJson(Arrays.asList("")));
      datastore.put(userEntity);
    }

    return userEntity;
  }

  private Entity getUserEntity(String userEmail) throws EntityNotFoundException {
    Key userKey = KeyFactory.createKey("User", userEmail);
    Entity userEntity = datastore.get(userKey);
    return userEntity;
  }
}
