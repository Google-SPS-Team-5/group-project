package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import com.google.sps.User;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that input user's data into Datastore */
@WebServlet("/usercreation")
public class UserCreationServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
  
  }
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
    Gson gson = new Gson();

    //Get input from form
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    List<String> favourites = Arrays.asList("");

    Entity userEntity = new Entity("User");
    userEntity.setProperty("name", name);
    userEntity.setProperty("email", email);
    userEntity.setProperty("favourites", gson.toJson(favourites));

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(userEntity);

    response.sendRedirect("/index.html");
  }
}