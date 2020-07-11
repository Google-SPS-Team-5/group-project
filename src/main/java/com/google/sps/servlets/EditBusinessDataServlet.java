
package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
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
import com.google.sps.Constants;
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
@WebServlet("/edit-business-data")
public class EditBusinessDataServlet extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Gson gson = new Gson();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    try {
    // Get the input from the form.
    Key businessKey = KeyFactory.createKey("Business", Constants.BUSINESS_KEY);
    String name = request.getParameter(Constants.BUSINESS_NAME);
    String desc = request.getParameter(Constants.BUSINESS_DESC);
    String[] categoriesArr = request.getParameterValues(Constants.BUSINESS_CATEGORIES);
    List<String> categories = categoriesArr == null ? new ArrayList<String>() : Arrays.asList(categoriesArr);
    String address = request.getParameter(Constants.BUSINESS_ADDRESS);
    String addressLat = request.getParameter(Constants.BUSINESS_ADDRESS_LAT);
    String addressLng = request.getParameter(Constants.BUSINESS_ADDRESS_LNG);
    String contactDetails = request.getParameter(Constants.BUSINESS_CONTACT_INFO);
    String orderDetails = request.getParameter(Constants.BUSINESS_ORDER_INFO);
    String businessLink = request.getParameter(Constants.BUSINESS_LINK);
    String menuLink = request.getParameter(Constants.BUSINESS_MENU_LINK);
    String logoUrl = getUploadedLogoUrlFromBlobstore(request, Constants.BUSINESS_LOGO);
    List<String> picturesUrls = getUploadedPicturesUrlsFromBlobstore(request, Constants.BUSINESS_PICTURES);
    // can't add reviews and rating when creating a new business
    String rating = "";
    List<String> reviews = new ArrayList<String>();

    Entity businessEntity = getBusinessEntity(datastore, businessKey);

    businessEntity.setProperty(Constants.BUSINESS_NAME, name);
    businessEntity.setProperty(Constants.BUSINESS_DESC, desc);
    businessEntity.setProperty(Constants.BUSINESS_CATEGORIES, gson.toJson(categories));
    businessEntity.setProperty(Constants.BUSINESS_ADDRESS, address);
    businessEntity.setProperty(Constants.BUSINESS_ADDRESS_LAT, addressLat);
    businessEntity.setProperty(Constants.BUSINESS_ADDRESS_LNG, addressLng);
    businessEntity.setProperty(Constants.BUSINESS_CONTACT_INFO, contactDetails);
    businessEntity.setProperty(Constants.BUSINESS_ORDER_INFO, orderDetails);
    businessEntity.setProperty(Constants.BUSINESS_LINK, businessLink);
    businessEntity.setProperty(Constants.BUSINESS_MENU_LINK, menuLink);
    businessEntity.setProperty(Constants.BUSINESS_LOGO, logoUrl);
    businessEntity.setProperty(Constants.BUSINESS_PICTURES, gson.toJson(picturesUrls));
    businessEntity.setProperty(Constants.BUSINESS_RATING, rating);
    businessEntity.setProperty(Constants.BUSINESS_REVIEWS, reviews);

    storeBusinessEntity(datastore, businessEntity);

    // Redirect back to the product page page.
    response.sendRedirect("/index.html");
    } catch (IOException err) {
      System.out.println(err);
    } catch (EntityNotFoundException err) {
      System.out.println(err);
    } 
  }

  private Entity getBusinessEntity(DatastoreService datastore, Key businessKey) throws EntityNotFoundException {
    Entity businessEntity = datastore.get(businessKey);
    return businessEntity;
  }


  private void storeBusinessEntity(DatastoreService datastore, Entity businessEntity) throws EntityNotFoundException {
    datastore.put(businessEntity);
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