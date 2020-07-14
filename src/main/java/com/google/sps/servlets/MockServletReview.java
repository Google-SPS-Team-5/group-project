package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.MockDataReview;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Exposes a list of reviews as a json object to a /mockdatareview GET query.
 */
@WebServlet("/mockdatareview")
public class MockServletReview extends HttpServlet {

  Gson gson = new Gson();

  MockDataReview mockDataReview = new MockDataReview();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    String json = gson.toJson(mockDataReview.mockDataList);

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
}

