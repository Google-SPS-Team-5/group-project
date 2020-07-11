package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/userinformation")
public class userServlet extends HttpServlet {

    Gson gson = new Gson();
    MockUserData mockUserData = new MockUserData();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;");
        response.getWriter().println("<h1>Hello world!</h1>");
      }
}