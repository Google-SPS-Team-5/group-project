package com.google.sps.servlets;

import com.google.gson.Gson;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Servlet that handles customer reviews.
 */
@WebServlet("/reviews")
public class ReviewsServlet extends HttpServlet {
  Gson gson = new Gson();
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  Long businessID = 404L;

  /** Get review from review form and store it in Datastore under its Business entity. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) {
    try {
      // Get review input from review form.
      String userID = getParameter(request, "name", "anonymous"); // Accept input name for now, should be user Datastore key, to be implemented later
      String comment = getParameter(request, "review", "No comment provided.");
      int rating = Integer.parseInt((String) request.getParameter("star-rating"));

      // Get current timestamp for time when review is posted.
      LocalDateTime dateTimeObj = LocalDateTime.now();
      DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
      String dateTime = dateTimeObj.format(format);

      // Get existing reviews key list.
      Key businessKey = KeyFactory.createKey("Business", this.businessID);
      Entity businessEntity = datastore.get(businessKey);
      Long[] reviewsIDArr = gson.fromJson((String) businessEntity.getProperty(BUSINESS_REVIEWS), Long[].class);
      List<Long> reviewsIDList;
      if (reviewsIDArr != null) {
        reviewsIDList = new ArrayList<Long>(Arrays.asList(reviewsIDArr));
      } else {
        reviewsIDList = new ArrayList<Long>();
      }

      // Create review entity and get its key.
      Entity reviewEntity = new Entity("Review");
      reviewEntity.setProperty(REVIEW_USERID, userID);
      reviewEntity.setProperty(REVIEW_COMMENT, comment);
      reviewEntity.setProperty(REVIEW_RATING, rating);
      reviewEntity.setProperty(REVIEW_DATETIME, dateTime);
      datastore.put(reviewEntity);
      Key reviewKey = reviewEntity.getKey();
      Long reviewID = reviewKey.getId();

      // Append key to its Business entity.
      reviewsIDList.add(reviewID);
      businessEntity.setProperty(BUSINESS_REVIEWS, gson.toJson(reviewsIDList));

      // Recalculate and update aggregated rating.
      float aggregatedRating = recalculateRating(businessEntity, reviewsIDList.size(), rating);
      businessEntity.setProperty(BUSINESS_RATING, aggregatedRating);
      datastore.put(businessEntity);

      // Redirect back to the product page.
      response.sendRedirect("/product.html?businessID=" + businessID);
    } catch (IOException err) {
        System.out.println(err);
    } catch (EntityNotFoundException err) {
        System.out.println(err);
    }
  }

  /**
   * Recalculates the mean (aggregate) rating for the current Business entity, based on the new review.
   * @param businessEntity business entity to update
   * @param numReviews total number of existing reviews, including the one just added
   * @param newRating the rating provided in the newest review
   * @return a float for the new aggregate rating
   */
  private float recalculateRating(Entity businessEntity, int numReviews, int newRating) {
    float oldAggregate = Float.parseFloat(String.valueOf(businessEntity.getProperty(BUSINESS_RATING)));
    if (oldAggregate == NOT_FOUND) { // a float (404) representing null value
      return newRating;
    }
    return (oldAggregate*(numReviews-1) + newRating) / numReviews;
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
  public void doGet(HttpServletRequest request, HttpServletResponse response) {
    try {
      // Query Datastore for reviews under the appropriate Business entity.
      List<Long> reviewsIDList = getReviewsIDList(request);
      List<Review> reviews = new ArrayList<Review>();

      // Convert review entities to objects and add to the list.
      for (Long reviewID : reviewsIDList) {
        Key reviewKey = KeyFactory.createKey("Review", reviewID);
        Entity reviewEntity = datastore.get(reviewKey);
        String userID = (String) reviewEntity.getProperty(REVIEW_USERID);
        String comment = (String) reviewEntity.getProperty(REVIEW_COMMENT);
        String dateTime = (String) reviewEntity.getProperty(REVIEW_DATETIME);
        int rating = Integer.parseInt(String.valueOf(reviewEntity.getProperty(REVIEW_RATING)));
        Review review = new Review(userID, comment, rating, dateTime);
        reviews.add(review);
      }

      // Sort the reviews by rating (descending order).
      reviews.sort(Comparator.comparingInt(Review::getRating).reversed());

      String json = convertToJsonUsingGson(reviews);
      response.setContentType("application/json;");
      response.getWriter().println(json);
    } catch (IOException err) {
        System.out.println(err);
    } catch (EntityNotFoundException err) {
        System.out.println(err);
    }
  }

  /**
   * Converts a Java ArrayList<Review> into a JSON string using the Gson library.
   */
  private String convertToJsonUsingGson(List<Review> reviewsList) {
    String json = gson.toJson(reviewsList);
    return json;
  }

  /**
   * Gets a list of Datastore keys corresponding to the Review entities for the current Business listing.
   * @return List<Key> of keys for review entities
   */
  private List<Long> getReviewsIDList(HttpServletRequest request) throws EntityNotFoundException {
    this.businessID = Long.parseLong(request.getParameter(BUSINESS_ID));
    Key businessKey = KeyFactory.createKey("Business", this.businessID);
    System.out.println("Retrieved businessID: " + this.businessID);
    Entity businessEntity = datastore.get(businessKey);
    Long[] reviewsKeyArr = gson.fromJson((String) businessEntity.getProperty(BUSINESS_REVIEWS), Long[].class);
    List<Long> reviewsIDList = new ArrayList<Long>();
    if (reviewsKeyArr != null) {
      reviewsIDList = new ArrayList<Long>(Arrays.asList(reviewsKeyArr));
    }
    return reviewsIDList;
  }
}
