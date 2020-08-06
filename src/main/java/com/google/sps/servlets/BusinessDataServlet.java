
package com.google.sps.servlets;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.images.ImagesServiceFailureException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.google.sps.Business;
import static com.google.sps.Constants.*;
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
  // Create gson for serializing and deserializing
  Gson gson = new Gson();
  // Create query for Datastore.
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  Query query = new Query("Business");

  /** Writes a JSON-ified list of all existing businesses from the Datastore
  */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    PreparedQuery results = datastore.prepare(query);

    // Iterate over results and add each business to the ArrayList.
    List<BusinessData> businessList = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      String name = (String) entity.getProperty(BUSINESS_NAME);
      String desc = (String) entity.getProperty(BUSINESS_DESC);
      String[] categoriesArr = gson.fromJson((String) entity.getProperty(BUSINESS_CATEGORIES), String[].class);
      List<String> categories = Arrays.asList(categoriesArr);
      String address = (String) entity.getProperty(BUSINESS_ADDRESS);
      float addressLat = ((Double) entity.getProperty(BUSINESS_ADDRESS_LAT)).floatValue();
      float addressLng = ((Double) entity.getProperty(BUSINESS_ADDRESS_LNG)).floatValue();
      String contactDetails = (String) entity.getProperty(BUSINESS_CONTACT_INFO);
      String orderDetails = (String) entity.getProperty(BUSINESS_ORDER_INFO);
      float minPrice = ((Double) entity.getProperty(BUSINESS_MIN_PRICE)).floatValue();
      float maxPrice = ((Double) entity.getProperty(BUSINESS_MAX_PRICE)).floatValue();
      String businessLink = (String) entity.getProperty(BUSINESS_LINK);
      String menuLink = (String) entity.getProperty(BUSINESS_MENU_LINK);
      String logoUrl = (String) entity.getProperty(BUSINESS_LOGO);
      List<String> picturesUrls = getPhotoUrlList(entity);
      float rating = ((Double) entity.getProperty(BUSINESS_RATING)).floatValue();
      String[] reviewsArr = gson.fromJson((String) entity.getProperty(BUSINESS_REVIEWS), String[].class);
      List<String> reviews = reviewsArr == null ? new ArrayList<String>() : Arrays.asList(reviewsArr);
  
      Business business = new Business(name, categories, minPrice, maxPrice, rating, addressLat, addressLng, address, logoUrl, picturesUrls, desc, menuLink, orderDetails, contactDetails, businessLink);
      BusinessData businessData = new BusinessData(gson.toJson(business), entity.getKey().getId());
      businessList.add(businessData);
    }

    // Send the JSON as the response.
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(businessList));
  }
  
  /**
  * Gets input from Add New Business form, creats a Business entity and stores it in the Datastore.
  */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Gson gson = new Gson();
    // Get the input from the form.
    String name = request.getParameter(BUSINESS_NAME);
    String desc = request.getParameter(BUSINESS_DESC);
    String[] categoriesArr = request.getParameterValues(BUSINESS_CATEGORIES);
    List<String> categories = categoriesArr == null ? new ArrayList<String>() : Arrays.asList(categoriesArr);
    String address = request.getParameter(BUSINESS_ADDRESS);
    float addressLat = getFloatParameter(request, BUSINESS_ADDRESS_LAT);
    float addressLng = getFloatParameter(request, BUSINESS_ADDRESS_LNG);
    String contactDetails = request.getParameter(BUSINESS_CONTACT_INFO);
    String orderDetails = request.getParameter(BUSINESS_ORDER_INFO);
    float minPrice = getFloatParameter(request, BUSINESS_MIN_PRICE);
    float maxPrice = getFloatParameter(request, BUSINESS_MAX_PRICE);
    String businessLink = request.getParameter(BUSINESS_LINK);
    String menuLink = request.getParameter(BUSINESS_MENU_LINK);
    List<String> logoUrlBlobList = getUploadedPicturesUrlsFromBlobstore(request, BUSINESS_LOGO);
    String logoUrl = logoUrlBlobList.isEmpty() ? "" : logoUrlBlobList.get(0);
    List<String> picturesUrls = getUploadedPicturesUrlsFromBlobstore(request, BUSINESS_PICTURES);
    // can't add reviews and rating when creating a new business
    float rating = getFloatParameter(request, BUSINESS_RATING);
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
    businessEntity.setProperty(BUSINESS_MIN_PRICE, minPrice);
    businessEntity.setProperty(BUSINESS_MAX_PRICE, maxPrice);
    businessEntity.setProperty(BUSINESS_LOGO, logoUrl);
    businessEntity.setProperty(BUSINESS_PICTURES, gson.toJson(picturesUrls));
    businessEntity.setProperty(BUSINESS_RATING, rating);
    businessEntity.setProperty(BUSINESS_REVIEWS, reviews);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(businessEntity);

    // Redirect back to the HTML page.
    response.sendRedirect("/index.html");
  }

  /** Gets the float value from the form, or 404 if none was inputted
  */
  private float getFloatParameter(HttpServletRequest request, String formElementName) {
    String floatStr = request.getParameter(formElementName);
    if (floatStr == null || floatStr.isEmpty()) {
      return 404;
    } else {
      return Float.parseFloat(floatStr);
    }
  }
 
  /** Gets the url of the business images from the blobstore, or empty string if no images were uploaded.
  */
  private List<String> getUploadedPicturesUrlsFromBlobstore(HttpServletRequest request, String formInputElementName)
    throws ImagesServiceFailureException {
    try {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
        List<BlobKey> blobKeys = blobs.get(formInputElementName);

        // if no images were uploaded
        if (blobKeys == null || blobKeys.size() == 0) {
            return new ArrayList<String>();
        }

        // Handle all blobkeys
        List<String> urls = new ArrayList<String>();


        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        for (BlobKey blobKey : blobKeys) {
        // We could check the validity of the file here, e.g. to make sure it's an image file
        // https://stackoverflow.com/q/10779564/873165

        // Use ImagesService to get a URL that points to the uploaded file.
        ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
        String url = imagesService.getServingUrl(options);

        // GCS's localhost preview is not actually on localhost,
        // so make the URL relative to the current domain.
        if(url.startsWith("http://localhost:8080/")){
            url = url.replace("http://localhost:8080/", "/");
        }
        
        urls.add(url);
        }

        return urls;
    } catch (ImagesServiceFailureException err) {
        // This exception is thrown when no images are uploaded. This exception is only thrown on the deployed server.
         return new ArrayList<String>();
    }
  }

  public class BusinessData {
    private final JsonElement data;
    private final Long id;

    JsonParser parser = new JsonParser();

    public BusinessData(String business, Long ID)
    {
      data = parser.parse(business);
      id = ID;
    }
  }

  private List<String> getPhotoUrlList(Entity entity) {
    String[] picturesUrlsArr;

    Object uncastedPhotoList = entity.getProperty(BUSINESS_PICTURES);
    if (uncastedPhotoList instanceof Text) {
      String castedPhotoListJson = ((Text) uncastedPhotoList).getValue();
      picturesUrlsArr = gson.fromJson(castedPhotoListJson, String[].class);
    } else {
      picturesUrlsArr = gson.fromJson((String) uncastedPhotoList, String[].class);
    }

    List<String> picturesUrls = picturesUrlsArr == null ? new ArrayList<String>() : Arrays.asList(picturesUrlsArr);
    return picturesUrls;
  }

}