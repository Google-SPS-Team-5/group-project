package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.sps.HomeBusiness;
import com.google.sps.HomeBusinessMockData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mockdata")
public class MockServlet extends HttpServlet {

  Gson gson = new Gson();
  HomeBusinessMockData mockData = new HomeBusinessMockData();
  // String businessName = "The Oven Bakes";
  // List<String> categoryList = Arrays.asList("Dessert", "Brownies", "Cookies");
  // float businessLat = 1.315120F;
  // float businessLong = 103.764977F; //This is Clementi MRT (their self collection location);
  // String businessAddr = "3150 Commonwealth Avenue West, 129580";
  // String businessLogoUrl = "https://res-1.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_120,w_120,f_auto,b_white,q_auto:eco/v1412921346/nnzir0xhukjpbpmctwy9.jpg";
  // List<String> businessPhotoUrlList = Arrays.asList("images/the-oven-bakes/the-oven-bakes-1.PNG","images/the-oven-bakes/the-oven-bakes-2.PNG","images/the-oven-bakes/the-oven-bakes-3.PNG","images/the-oven-bakes/the-oven-bakes-5.PNG");

  // String businessDescr = "The Oven Bakes is a local home business that makes mouth-watering brownies and cookies." +
  //                         "These homemade treats are sure going to sweeten your day." +
  //                         "Baked goodies are limited in stock and only available for pre-order. Grab yours soon!\n\n" +
  //                         "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
  //                         "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
  //                         "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit" +
  //                         "esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in" +
  //                         "culpa qui officia deserunt mollit anim id est laborum.";

  // String menuUrl = "https://www.instagram.com/theovenbakes/";
  // String businessOrderInfo = "Minimum Order(for delivery): $15\n\n" +
  //                             "Islandwide Delivery: $10";
  // String businessContactUrl = "https://www.instagram.com/theovenbakes/";
  // String linkToBusiness = "https://www.instagram.com/theovenbakes/";

  // HomeBusiness mockBusiness = new HomeBusiness(businessName, 
  //                                         categoryList, 
  //                                         businessLat, 
  //                                         businessLong, 
  //                                         businessAddr, 
  //                                         businessLogoUrl, 
  //                                         businessPhotoUrlList,
  //                                         businessDescr,
  //                                         menuUrl,
  //                                         businessOrderInfo,
  //                                         businessContactUrl,
  //                                         linkToBusiness);

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    String json = gson.toJson(mockData.mockDataList.get(0));

    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
}
