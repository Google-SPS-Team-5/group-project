package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import static com.google.sps.Constants.*;
import com.google.sps.Review;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Servlet that handles customer reviews.
 */
@WebServlet("/reviews")
public class ReviewsServlet extends HttpServlet {
  Gson gson = new Gson();
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  /** Get review from review form and store it in Datastore under its Business entity. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get review input from review form.
    String userID = getParameter(request, "name", "anonymous"); // Accept input name for now, should be user Datastore key, to be implemented later
    String comment = getParameter(request, "review", "No comment provided.");
    int rating = request.getParameter("rating");

    // Get current timestamp for time when review is posted.
    LocalDateTime dateTime = LocalDateTime.now();

    // Get existing reviews key list.
    List<Key> reviewsKeyList = getReviewsKeyList();

    // Create review entity and get its key.
    Entity reviewEntity = new Entity("Review");
    reviewEntity.setProperty("userID", userID);
    reviewEntity.setProperty("comment", comment);
    reviewEntity.setProperty("rating", rating);
    reviewEntity.setProperty("dateTime", dateTime);
    datastore.put(reviewEntity);
    Key reviewKey = reviewEntity.getKey();

    // Append key to its Business entity.
    reviewsKeyList.add(reviewKey);
    businessEntity.setProperty(BUSINESS_REVIEWS, gson.toJson(reviewsKeyList));

    // Redirect back to the product page.
    response.sendRedirect("/product.html?businessID="+businessKey);
  }

  /**
   * @return the String request parameter, or the default value if the parameter
   *         was not specified by the client
   */
  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value.isEmpty()) {
      return defaultValue;
    }
    return value;
  }

  /** Get reviews from Datastore and send them as a Json to reviews.js. */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Query Datastore for reviews under the appropriate Business entity.
    List<Key> reviewsKeyList = getReviewsKeyList();
    ArrayList<Review> reviews = new ArrayList<Review>();

    // Convert review entities to objects and add to the list.
    for (Key reviewKey : reviewsKeyList) {
      Entity reviewEntity = datastore.get(reviewKey);
      String userID = (String) reviewEntity.getProperty(REVIEW_USERID);
      String comment = (String) reviewEntity.getProperty(REVIEW_COMMENT);
      LocalDateTime dateTime = (String) reviewEntity.getProperty(REVIEW_DATETIME);
      int rating = reviewEntity.getProperty(REVIEW_RATING);
      Review review = new Review(userID, comment, rating, dateTime);
      reviews.add(review);
    }

    String json = convertToJsonUsingGson(reviews);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  /**
   * Converts a Java ArrayList<Review> into a JSON string using the Gson library.
   */
  private String convertToJsonUsingGson(ArrayList<Review> reviewsList) {
    String json = gson.toJson(reviewsList);
    return json;
  }

  /**
   * Gets a list of Datastore keys corresponding to the Review entities for the current Business listing.
   * @return List<Key> of keys for review entities
   */
  private List<Key> getReviewsKeyList() {
    Key businessKey = KeyFactory.createKey("Business", Long.parseLong(request.getParameter(BUSINESS_KEY))); // KIV url parameter
    Entity businessEntity = datastore.get(businessKey);
    Key[] reviewsKeyArr = gson.fromJson(businessEntity.getProperty(BUSINESS_REVIEWS), Key[].class);
    List<Key> reviewsKeyList = new ArrayList<Key>(Arrays.asList(reviewsKeyArr));
    return reviewsKeyList;
  }
}
