package com.google.sps;

import java.net.URL;    
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Collections;

public final class Business {

  private String name;
  private List<String> categories = new ArrayList<String>();
  private float aggregatedRating;
  private float addressLng;
  private float addressLat;
  private String address;
  private String logoBlobstoreUrl;
  private List<String> photoBlobstoreUrlList = new ArrayList<String>();
  private String description;
  private String menuUrl;
  private String orderInformation;
  private String contactUrl;
  private String websiteUrl;
  private List<String> reviewDatastoreKeyList = new ArrayList<String>();
  
  // TODO: convert urls to URL type (if possible in datastore)
  public Business(String businessName, 
                      List<String> categoryList,
                      float rating,
                      float businessLat, 
                      float businessLong, 
                      String businessAddr, 
                      String businessLogoUrl, 
                      List<String> businessPhotoUrlList,
                      String businessDescr,
                      String menuUrl,
                      String businessOrderInfo,
                      String businessContactUrl,
                      String linkToBusiness)
  {
    name = businessName;
    categories = categoryList;
    aggregatedRating = rating;
    addressLng = businessLat;
    addressLat = businessLong;
    address = businessAddr;
    logoBlobstoreUrl = businessLogoUrl;
    photoBlobstoreUrlList = businessPhotoUrlList;
    description = businessDescr;
    menuUrl = menuUrl;
    orderInformation = businessOrderInfo;
    contactUrl = businessContactUrl;
    websiteUrl = linkToBusiness;
  }

  @Override
  public String toString() {
    return String.format("Business: %s, Rating: %f", name, aggregatedRating);
  }
}
