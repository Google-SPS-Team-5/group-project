package com.google.sps;

public final class Constants {

  // For querying the business entities
  public static final String BUSINESS_ID = "businessID";
  public static final String BUSINESS_NAME = "name";
  public static final String BUSINESS_DESC = "desc";
  public static final String BUSINESS_CATEGORIES = "categories";
  public static final String BUSINESS_ADDRESS = "address";
  public static final String BUSINESS_ADDRESS_LAT = "addressLat";
  public static final String BUSINESS_ADDRESS_LNG = "addressLng";
  public static final String BUSINESS_CONTACT_INFO = "contactDetails";
  public static final String BUSINESS_ORDER_INFO = "orderDetails";
  public static final String BUSINESS_MIN_PRICE = "minPrice";
  public static final String BUSINESS_MAX_PRICE = "maxPrice";
  public static final String BUSINESS_LINK = "businessLink";
  public static final String BUSINESS_MENU_LINK = "menuLink";
  public static final String BUSINESS_LOGO = "logo";
  public static final String BUSINESS_EXISTING_LOGO = "existingLogo";
  public static final String BUSINESS_PICTURES = "pictures";
  public static final String BUSINESS_EXISTING_PICTURES = "existingPictures";
  public static final String BUSINESS_RATING = "rating";
  public static final String BUSINESS_REVIEWS = "reviews";
  public static final float NOT_FOUND = (float) 404;

  // For querying the review entities
  public static final String REVIEW_USERID = "userID";
  public static final String REVIEW_USERNAME = "name";
  public static final String REVIEW_COMMENT = "comment";
  public static final String REVIEW_DATETIME = "dateTime";
  public static final String REVIEW_RATING = "rating";

  // For querying the user entities
  public static final String USER_NAME = "name";
  public static final String USER_EMAIL = "email";
  public static final String USER_FAVOURITES = "favourites";
  public static final String USER_DID_FAVOURITE = "did_favourite";
}
