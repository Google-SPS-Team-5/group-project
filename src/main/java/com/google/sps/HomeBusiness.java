package com.google.sps;

import java.net.URL;    
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Collections;

public final class HomeBusiness {

  private String homeBusinessName;
  private List<String> businessCategoriesList = new ArrayList<String>();
  private float aggregatedReviewRating;
  private float businessAddressLongitude;
  private float businessAddressLatitude;
  private String businessAddress;
  private String businessLogoBlobstoreUrl;
  private List<String> businessPhotoBlobstoreUrlList = new ArrayList<String>();
  private String businessDescription;
  private String businessMenuUrl;
  private String businessOrderInformation;
  private String businessContactUrl;
  private String businessUrl;
  private List<String> reviewDatastoreKeyList = new ArrayList<String>();
  
  // TODO: convert urls to URL type (if possible in datastore)
  public HomeBusiness(String businessName, 
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
    homeBusinessName = businessName;
    businessCategoriesList = categoryList;
    aggregatedReviewRating = rating;
    businessAddressLongitude = businessLat;
    businessAddressLatitude = businessLong;
    businessAddress = businessAddr;
    businessLogoBlobstoreUrl = businessLogoUrl;
    businessPhotoBlobstoreUrlList = businessPhotoUrlList;
    businessDescription = businessDescr;
    businessMenuUrl = menuUrl;
    businessOrderInformation = businessOrderInfo;
    businessContactUrl = businessContactUrl;
    businessUrl = linkToBusiness;
  }

  @Override
  public String toString() {
    return String.format("Business: %s, Rating: %f", homeBusinessName, aggregatedReviewRating);
  }
}
