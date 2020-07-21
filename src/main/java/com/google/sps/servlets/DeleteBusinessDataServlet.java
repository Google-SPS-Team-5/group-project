
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
import static com.google.sps.Constants.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;  
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that deletes an entity from the datastore
*/
@WebServlet("/delete-business")
public class DeleteBusinessDataServlet extends HttpServlet {

  Gson gson = new Gson();
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
 
  /** Gets the business id from the form, creates key, and deletes the entity by key  */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Long businessId = Long.parseLong(request.getParameter("businessID-delete-form"));
    Key businessKey = KeyFactory.createKey("Business", businessId);
    datastore.delete(businessKey);

    // Redirect back to the product page.
    response.sendRedirect("/index.html");
  }
}