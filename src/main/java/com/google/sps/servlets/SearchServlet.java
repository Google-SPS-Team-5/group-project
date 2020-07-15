package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.Business;
import com.google.sps.MockDataBusiness;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    Gson gson = new Gson();
    MockDataBusiness mockDataBusiness = new MockDataBusiness();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String resultJson;
        resp.setContentType("application/json;");

        // Get Parameters From The Request
        String searchQuery = req.getParameter("s");
        if (searchQuery == null || "".equals(searchQuery)) {
            resultJson = gson.toJson(mockDataBusiness.mockDataList);
        } else {
            List<String> words = Arrays.asList(searchQuery.toLowerCase().split(" "));
            List<Business> searchedResult = mockDataBusiness.mockDataList
                    .stream()
                    .filter(business -> words.stream()
                    .anyMatch(word ->
                            business.getBusinessName().toLowerCase().contains(word)))
                    .collect(Collectors.toList());
            resultJson = gson.toJson(searchedResult);
        }
        resp.getWriter().println(resultJson);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String searchQuery = request.getParameter("query");
        if (searchQuery == null || searchQuery.trim().length() == 0) {
            response.sendRedirect("/");
        }

        response.sendRedirect("/search?s=" + searchQuery);
    }
}
