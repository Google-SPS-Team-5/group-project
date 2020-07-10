package com.google.sps;

import com.google.sps.Review;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Temporary class to hold review mock data for testing purposes. Will be removed when we have actual review data.
 */
public class MockDataReview {

  public MockDataReview()
  {
    String userID = "Valerie Ng"; // This should be a datastore key, but is a hardcoded name for now
    String comment = "There are many variations of passages of Lorem Ipsum available, " +
            "but the majority have suffered alteration in some form, by injected humour, " +
            "or randomised words which don't look even slightly believable.";
    int rating = 4;
    LocalDateTime dateTime = LocalDateTime.now();

    Review mockReview1 = new Review(userID, comment, rating, dateTime);

    userID = "John Doe";
    comment = "I love the sea salt brownies! My first time having sea salt on a dessert and I was mind blown!";
    rating = 5;
    dateTime = LocalDateTime.of(2017, 1, 14, 10, 34);

    Review mockReview2 = new Review(userID, comment, rating, dateTime);

    userID = "Lubert Claine Dolfeschlegelsteinhausenbergerdorff Sr.";
    comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
    rating = 3;
    dateTime = LocalDateTime.of(2018, 11, 12, 0, 0);

    Review mockReview3 = new Review(userID, comment, rating, dateTime);

    userID = "Tan Ah Beng";
    comment = "Quam nulla porttitor massa id neque aliquam. Ultrices mi tempus imperdiet nulla malesuada. " +
            "Eros in cursus turpis massa tincidunt dui ut ornare lectus.";
    rating = 2;
    dateTime = LocalDateTime.of(2020, 3, 14, 23, 59);

    Review mockReview4 = new Review(userID, comment, rating, dateTime);

    userID = "Tan Ah Lian";
    comment = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, " +
            "totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et " +
            "quasi architecto beatae vitae dicta sunt explicabo.";
    rating = 1;
    dateTime = LocalDateTime.of(2020, 7, 3, 10, 34);

    Review mockReview5 = new Review(userID, comment, rating, dateTime);

    userID = "Sally Stitches";
    comment = "Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, " +
            "nisi ut aliquid ex ea commodi consequatur?";
    rating = 5;
    dateTime = LocalDateTime.of(2019, 1, 14, 10, 34);

    Review mockReview6 = new Review(userID, comment, rating, dateTime);

    userID = "Capybara";
    comment = "I love the sea salt brownies! I didn't know if capybaras could eat sea salt brownies, " +
            "but I just tested it and it was amazing!";
    rating = 4;
    dateTime = LocalDateTime.of(2020, 7, 10, 14, 34);

    Review mockReview7 = new Review(userID, comment, rating, dateTime);

    mockDataList = Arrays.asList(mockReview1, mockReview2, mockReview3, mockReview4, mockReview5, mockReview6, mockReview7);
  }

  /**
   * Access mock data through this list.
   */
  public List<Review> mockDataList = new ArrayList<>();
}
