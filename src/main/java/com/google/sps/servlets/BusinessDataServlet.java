
package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.google.sps.Business;
import com.google.sps.Constants.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;  
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns all businesses and lets you create a new business */
@WebServlet("/business-data")
public class BusinessDataServlet extends HttpServlet {
    
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Create gson for serializing and deserializing
    Gson gson = new Gson();

    // Create query for Datastore.
    Query query = new Query("Business");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    // Iterate over results and add each business to the ArrayList.
    List<Business> businesses = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      String name = (String) entity.getProperty(BUSINESS_NAME);
      String desc = (String) entity.getProperty(BUSINESS_DESC);
      String[] categoriesArr = gson.fromJson((String) entity.getProperty(BUSINESS_CATEGORIES), String[].class);
      List<String> categories = Arrays.asList(categoriesArr);
      String address = (String) entity.getProperty(BUSINESS_ADDRESS);
      String addressLatStr = (String) entity.getProperty(BUSINESS_ADDRESS_LAT);
      String addressLngStr = (String) entity.getProperty(BUSINESS_ADDRESS_LNG);
      float addressLat;
      if (addressLatStr.isEmpty()) {
          addressLat = 404;
      } else {
          addressLat = Float.parseFloat(addressLatStr);
      }
      float addressLng;
      if (addressLngStr.isEmpty()) {
          addressLng = 404;
      } else {
          addressLng = Float.parseFloat(addressLngStr);
      }
      String contactDetails = (String) entity.getProperty(BUSINESS_CONTACT_INFO);
      String orderDetails = (String) entity.getProperty(BUSINESS_ORDER_INFO);
      String businessLink = (String) entity.getProperty(BUSINESS_LINK);
      String menuLink = (String) entity.getProperty(BUSINESS_MENU_LINK);
      String logoUrl = (String) entity.getProperty(BUSINESS_LOGO);
      String[] picturesUrlsArr = gson.fromJson((String) entity.getProperty(BUSINESS_PICTURES), String[].class);
      List<String> picturesUrls = picturesUrlsArr == null ? new ArrayList<String>() : Arrays.asList(picturesUrlsArr);
      String ratingStr = (String) entity.getProperty(BUSINESS_RATING);
      float rating;
      if (ratingStr.isEmpty()) {
          rating = 404;
      } else {
          rating = Float.parseFloat(ratingStr);
      }
      String[] reviewsArr = gson.fromJson((String) entity.getProperty(BUSINESS_REVIEWS), String[].class);
      List<String> reviews = reviewsArr == null ? new ArrayList<String>() : Arrays.asList(reviewsArr);

  
      Business business = new Business(name, categories, rating, addressLat, addressLng, address, logoUrl, picturesUrls, desc, menuLink, orderDetails, contactDetails, businessLink);
      businesses.add(business);
    }

    // Send the JSON as the response.
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(businesses));
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Gson gson = new Gson();
    // Get the input from the form.
    String name = request.getParameter(BUSINESS_NAME);
    String desc = request.getParameter(BUSINESS_DESC);
    String[] categoriesArr = request.getParameterValues(BUSINESS_CATEGORIES);
    List<String> categories = categoriesArr == null ? new ArrayList<String>() : Arrays.asList(categoriesArr);
    String address = request.getParameter(BUSINESS_ADDRESS);
    String addressLat = request.getParameter(BUSINESS_ADDRESS_LAT);
    String addressLng = request.getParameter(BUSINESS_ADDRESS_LNG);
    String contactDetails = request.getParameter(BUSINESS_CONTACT_INFO);
    String orderDetails = request.getParameter(BUSINESS_ORDER_INFO);
    String businessLink = request.getParameter(BUSINESS_LINK);
    String menuLink = request.getParameter(BUSINESS_MENU_LINK);
    String logoUrl = getUploadedLogoUrlFromBlobstore(request, BUSINESS_LOGO);
    List<String> picturesUrls = getUploadedPicturesUrlsFromBlobstore(request, BUSINESS_PICTURES);
    // can't add reviews and rating when creating a new business
    String rating = "";
    List<String> reviews = new ArrayList<String>();

    Entity businessEntity = new Entity("Business");
    businessEntity.setProperty(BUSINESS_NAME, name);
    businessEntity.setProperty(BUSINESS_DESC, desc);
    businessEntity.setProperty(BUSINESS_CATEGORIES, gson.toJson(categories));
    businessEntity.setProperty(BUSINESS_ADDRESS, address);
    businessEntity.setProperty(BUSINESS_ADDRESS_LAT, addressLat);
    businessEntity.setProperty(BUSINESS_ADDRESS_LNG, addressLng);
    businessEntity.setProperty(BUSINESS_CONTACT_INFO, contactDetails);
    businessEntity.setProperty(BUSINESS_ORDER_INFO, orderDetails);
    businessEntity.setProperty(BUSINESS_LINK, businessLink);
    businessEntity.setProperty(BUSINESS_MENU_LINK, menuLink);
    businessEntity.setProperty(BUSINESS_LOGO, logoUrl);
    businessEntity.setProperty(BUSINESS_PICTURES, gson.toJson(picturesUrls));
    businessEntity.setProperty(BUSINESS_RATING, rating);
    businessEntity.setProperty(BUSINESS_REVIEWS, reviews);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(businessEntity);

    // Redirect back to the HTML page.
    response.sendRedirect("/index.html");
  }

  private String getUploadedLogoUrlFromBlobstore(HttpServletRequest request, String formInputElementName) {
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get(formInputElementName);

    // if no images were uploaded
    if (blobKeys == null) {
        return "";
    }

    // Our form only contains a single file input, so get the first index.
    BlobKey blobKey = blobKeys.get(0);

    // We could check the validity of the file here, e.g. to make sure it's an image file
    // https://stackoverflow.com/q/10779564/873165

    // Use ImagesService to get a URL that points to the uploaded file.
    ImagesService imagesService = ImagesServiceFactory.getImagesService();
    ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
    String url = imagesService.getServingUrl(options);

    System.out.println(url);
    // GCS's localhost preview is not actually on localhost,
    // so make the URL relative to the current domain.
    if(url.startsWith("http://localhost:8080/")){
      url = url.replace("http://localhost:8080/", "/");
    }
    return url;
  }

  private List<String> getUploadedPicturesUrlsFromBlobstore(HttpServletRequest request, String formInputElementName) {
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = blobs.get(formInputElementName);

    // if no images were uploaded
    if (blobKeys == null) {
        return new ArrayList<String>();
    }

    // Handle all blobkeys
    List<String> urls = new ArrayList<String>();

    for (BlobKey blobKey : blobKeys) {
      // We could check the validity of the file here, e.g. to make sure it's an image file
      // https://stackoverflow.com/q/10779564/873165

      // Use ImagesService to get a URL that points to the uploaded file.
      ImagesService imagesService = ImagesServiceFactory.getImagesService();
      ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
      String url = imagesService.getServingUrl(options);

      System.out.println(url);
      // GCS's localhost preview is not actually on localhost,
      // so make the URL relative to the current domain.
      if(url.startsWith("http://localhost:8080/")){
        url = url.replace("http://localhost:8080/", "/");
      }
    
      urls.add(url);
    }

    return urls;
  }

}