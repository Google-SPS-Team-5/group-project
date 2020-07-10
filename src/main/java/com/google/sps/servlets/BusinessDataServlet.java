
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
import com.google.sps.Business;
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
    
//   @Override
//   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
//     // Create query for Datastore.
//     Query query = new Query("Business");

//     DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
//     PreparedQuery results = datastore.prepare(query);

//     // Iterate over results and add each comment to the ArrayList.
//     List<Business> businesses = new ArrayList<>();
//     for (Entity entity : results.asIterable()) {
//       long id = entity.getKey().getId();
//       String username = (String) entity.getProperty("username");
//       String comments = (String) entity.getProperty("comments");
//       String timestamp = (String) entity.getProperty("timestamp");
  
//       Business business = new Business();
//       businesses.add(business);
//     }

//     Gson gson = new Gson();

//     // Send the JSON as the response.
//     response.setContentType("application/json;");
//     response.getWriter().println(gson.toJson(businesses));
//   }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Gson gson = new Gson();
    // Get the input from the form.
    String name = request.getParameter("name");
    String desc = request.getParameter("desc");
    List<String> categories = Arrays.asList(request.getParameterValues("categories"));
    String address = request.getParameter("address");
    String addressLat = request.getParameter("addressLat");
    String addressLng = request.getParameter("addressLng");
    String contactDetails = request.getParameter("contactDetails");
    String orderDetails = request.getParameter("orderDetails");
    String businessLink = request.getParameter("businessLink");
    String menuLink = request.getParameter("menuLink");
    String logoUrl = getUploadedLogoUrlFromBlobstore(request, "logo");
    List<String> picturesUrls = getUploadedPicturesUrlsFromBlobstore(request, "pictures");
    // can't add reviews and rating when creating a new business
    int rating = 0;
    List<String> reviews = new ArrayList<String>();

    Entity businessEntity = new Entity("Business");
    businessEntity.setProperty("name", name);
    businessEntity.setProperty("desc", desc);
    businessEntity.setProperty("categories", gson.toJson(categories));
    businessEntity.setProperty("address", address);
    businessEntity.setProperty("addressLat", addressLat);
    businessEntity.setProperty("addressLng", addressLng);
    businessEntity.setProperty("addressLat", addressLat);
    businessEntity.setProperty("orderDetails", orderDetails);
    businessEntity.setProperty("businessLink", businessLink);
    businessEntity.setProperty("menuLink", menuLink);
    businessEntity.setProperty("logoUrl", logoUrl);
    businessEntity.setProperty("picturesUrls", gson.toJson(picturesUrls));
    businessEntity.setProperty("rating", rating);
    businessEntity.setProperty("reviews", reviews);

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