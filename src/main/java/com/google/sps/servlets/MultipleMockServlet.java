package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.MockData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* Exposes a list of businesses as a json object to a /multiplemockdata GET query.
*/
@WebServlet("/multiplemockdata")
public class MultipleMockServlet extends HttpServlet {

  Gson gson = new Gson();

  MockData mockData = new MockData();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    String json = gson.toJson(mockData.mockDataList);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
}
