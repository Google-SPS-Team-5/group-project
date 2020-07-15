package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet {

  private final String USER_JSON_DETAILS = "{ \"userEmail\": \"%s\", \"url\": \"%s\", \"isAdmin\": \"%s\"}";
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json;");
    UserService userService = UserServiceFactory.getUserService();

    if (userService.isUserLoggedIn()) {
      String userEmail = userService.getCurrentUser().getEmail();
      String urlToRedirectToAfterUserLogsOut = request.getHeader("referer");
      Boolean isAdmin = userService.isUserAdmin();
      String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);

      String json = String.format(USER_JSON_DETAILS, userEmail, logoutUrl, isAdmin);
      response.getWriter().println(json);
    } else {
      String urlToRedirectToAfterUserLogsIn = request.getHeader("referer");
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);

      String json = String.format(USER_JSON_DETAILS, "", loginUrl, "");
      response.getWriter().println(json);
    }
  }
}
