package com.google.sps;

/**
 * Holds all relevant data to a business review on the site.
 */
public final class Review {
  private String userID; // Datastore key for the user that this review belongs to
  private String comment;
  private int rating;
  private String dateTime;

  public Review(String userID,
                String comment,
                int rating,
                String dateTime) {
    this.userID = userID;
    this.comment = comment;
    this.rating = rating;
    this.dateTime = dateTime;
  }

  public int getRating() {
    return rating;
  }

  @Override
  public String toString() {
    return String.format("Comment: %s, Rating: %d, Time: %s", this.comment, this.rating, this.dateTime);
  }
}
