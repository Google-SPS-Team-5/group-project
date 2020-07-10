package com.google.sps;

import java.time.LocalDate;

/**
 * Holds all relevant data to a business review on the site.
 */
public final class Review {
  private String userID; // Datastore key for the user that this review belongs to
  private String comment;
  private int rating;
  private LocalDate dateTime;

  public Review(String userID,
                String comment,
                int rating,
                LocalDate dateTime) {
    this.userID = userID;
    this.comment = comment;
    this.rating = rating;
    this.dateTime = dateTime;
  }

  @Override
  public String toString() {
    return String.format("Comment: %s, Rating: %d", this.comment, this.rating);
  }
}
