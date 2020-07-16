
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
import com.google.appengine.api.images.ImagesServiceFailureException;
import com.google.gson.Gson;
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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.*;
import java.text.SimpleDateFormat;  
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns a specific business and lets you edit that specific business */
@WebServlet("/edit-business-data")
public class EditBusinessDataServlet extends HttpServlet {

  Gson gson = new Gson();
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
 
  /** Writes a JSON-ified of a specific from the Datastore. The key is taken from the URL parameter.
  */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
        Long businessId = Long.parseLong(request.getParameter("businessID"));
        Key businessKey = KeyFactory.createKey("Business", businessId);    
        Entity businessEntity = getBusinessEntity(datastore, businessKey);

        // Get the input from the form
        String name = (String) businessEntity.getProperty(BUSINESS_NAME);
        String desc = (String) businessEntity.getProperty(BUSINESS_DESC);
        String[] categoriesArr = gson.fromJson((String) businessEntity.getProperty(BUSINESS_CATEGORIES), String[].class);
        List<String> categories = Arrays.asList(categoriesArr);
        String address = (String) businessEntity.getProperty(BUSINESS_ADDRESS);
        float addressLat = ((Double) businessEntity.getProperty(BUSINESS_ADDRESS_LAT)).floatValue();
        float addressLng = ((Double) businessEntity.getProperty(BUSINESS_ADDRESS_LNG)).floatValue();
        String contactDetails = (String) businessEntity.getProperty(BUSINESS_CONTACT_INFO);
        String orderDetails = (String) businessEntity.getProperty(BUSINESS_ORDER_INFO);
        float minPrice = ((Double) businessEntity.getProperty(BUSINESS_MIN_PRICE)).floatValue();
        float maxPrice = ((Double) businessEntity.getProperty(BUSINESS_MAX_PRICE)).floatValue();
        String businessLink = (String) businessEntity.getProperty(BUSINESS_LINK);
        String menuLink = (String) businessEntity.getProperty(BUSINESS_MENU_LINK);
        String logoUrl = (String) businessEntity.getProperty(BUSINESS_LOGO);
        String[] picturesUrlsArr = gson.fromJson((String) businessEntity.getProperty(BUSINESS_PICTURES), String[].class);
        List<String> picturesUrls = picturesUrlsArr == null ? new ArrayList<String>() : Arrays.asList(picturesUrlsArr);
        float rating = ((Double) businessEntity.getProperty(BUSINESS_RATING)).floatValue();
        String[] reviewsArr = gson.fromJson((String) businessEntity.getProperty(BUSINESS_REVIEWS), String[].class);
        List<String> reviews = reviewsArr == null ? new ArrayList<String>() : Arrays.asList(reviewsArr);


        Business business = new Business(name, categories, minPrice, maxPrice, rating, addressLat, addressLng, address, logoUrl, picturesUrls, desc, menuLink, orderDetails, contactDetails, businessLink);
        String businessJson = String.format("{\"data\" : %s, \"id\": %s }", gson.toJson(business), businessEntity.getKey().getId());
        // Send the JSON as the response.
        response.setContentType("application/json;");
        response.getWriter().println(businessJson);
    } catch (IOException err) {
        System.out.println(err);
    } catch (EntityNotFoundException err) {
        System.out.println(err);
    }
  }


  /** Gets input from the form, including the business key, retrieves the business matching the key
  * and updates it with the fields from the form.
  */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
    Long businessId = Long.parseLong(request.getParameter(BUSINESS_ID));
    Key businessKey = KeyFactory.createKey("Business", businessId);
     // Get the input from the form
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
    
    String existingLogo = request.getParameter(BUSINESS_EXISTING_LOGO);
    List<String> logoUrlBlobList = getUploadedPicturesUrlsFromBlobstore(request, BUSINESS_LOGO);
    String logoUrl = prepareLogoUrl(existingLogo, logoUrlBlobList);

    String[] existingPicturesUrls = request.getParameter(BUSINESS_EXISTING_PICTURES).split(",");
    List<String> newPicturesUrls = getUploadedPicturesUrlsFromBlobstore(request, BUSINESS_PICTURES);
    List<String> picturesUrls = preparePicturesUrls(existingPicturesUrls, newPicturesUrls);

    Entity businessEntity = getBusinessEntity(datastore, businessKey);
    
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

    storeBusinessEntity(datastore, businessEntity);

    // Redirect back to the product page.
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

  /** Determine which logo url to send back to the datastore
  */
  private String prepareLogoUrl(String existingUrl, List<String> newUrls) {
      // Handle strange behaviour in dev server where the url always gets "/_cloudshellProxy" prepended to it upon form submission
      // so the img src will eventually become something like "/_cloudshellProxy/_cloudshellProxy/_cloudshellProxy/_ah/blobKey".
      // So I will just return the part after "/_cloudshellProxy"
      // This issue does not happen on the deployed server.
      return newUrls.isEmpty()
        ? existingUrl.startsWith("/_cloudshellProxy")
         ? existingUrl.substring(18)
         : existingUrl
      : newUrls.get(0);
  }
  
  /** Appends the newly uplaoded urls to the urls of existing images. 
  */
  private List<String> preparePicturesUrls(String[] existingUrlsArr, List<String> newUrls) {
      if (existingUrlsArr == null || existingUrlsArr.length == 0) {
          return newUrls;
      } else {
          List<String> existingUrls = Arrays.asList(existingUrlsArr);
          // Handle strange behaviour in dev server where the url always gets "/_cloudshellProxy" prepended to it upon form submission
          // so the img src will eventually become something like "/_cloudshellProxy/_cloudshellProxy/_cloudshellProxy/_ah/blobKey".
          // So I will just return the part after "/_cloudshellProxy"
          // This issue does not happen on the deployed server.
          List<String> sanitisedExistingUrls = existingUrls
                                                .stream()
                                                .map(s -> s.startsWith("/_cloudshellProxy")
                                                    ? s.substring(18)
                                                    : s)
                                                .collect(Collectors.toList());
          return Stream.concat(sanitisedExistingUrls.stream(), newUrls.stream()).collect(Collectors.toList());
      }
  }

  /** Gets the url of the business images from the blobstore, or empty string if no images were uploaded.
  */
  private List<String> getUploadedPicturesUrlsFromBlobstore(HttpServletRequest request, String formInputElementName) throws ImagesServiceFailureException {
    try {
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
        List<BlobKey> blobKeys = blobs.get(formInputElementName);

        // if no images were uploaded
        if (blobKeys == null || blobKeys.isEmpty()) {
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

}