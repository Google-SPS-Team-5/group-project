package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.MockDataBusiness;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* Exposes a list of businesses as a json object to a /multiplemockdatabusiness GET query.
*/
@WebServlet("/multiplemockdatabusiness")
public class MultipleMockServletBusiness extends HttpServlet {

  Gson gson = new Gson();

  MockDataBusiness mockDataBusiness = new MockDataBusiness();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    String json = gson.toJson(mockDataBusiness.mockDataList);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
}
