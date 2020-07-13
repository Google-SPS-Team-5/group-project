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
    String dateTime = "12-12-2020 00:00:00";

    Review mockReview1 = new Review(userID, comment, rating, dateTime);

    userID = "John Doe";
    comment = "I love the sea salt brownies! My first time having sea salt on a dessert and I was mind blown!";
    rating = 5;
    dateTime = "12-07-2019 12:00:00";

    Review mockReview2 = new Review(userID, comment, rating, dateTime);

    userID = "Lubert Claine Dolfeschlegelsteinhausenbergerdorff Sr.";
    comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
    rating = 3;
    dateTime = "11-04-2015 10:07:23";

    Review mockReview3 = new Review(userID, comment, rating, dateTime);

    userID = "Tan Ah Beng";
    comment = "Quam nulla porttitor massa id neque aliquam. Ultrices mi tempus imperdiet nulla malesuada. " +
            "Eros in cursus turpis massa tincidunt dui ut ornare lectus.";
    rating = 2;
    dateTime = "07-07-2017 07:07:07";

    Review mockReview4 = new Review(userID, comment, rating, dateTime);

    userID = "Tan Ah Lian";
    comment = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, " +
            "totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et " +
            "quasi architecto beatae vitae dicta sunt explicabo.";
    rating = 1;
    dateTime = "06-07-2019 01:00:00";

    Review mockReview5 = new Review(userID, comment, rating, dateTime);

    userID = "Sally Stitches";
    comment = "Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, " +
            "nisi ut aliquid ex ea commodi consequatur?";
    rating = 5;
    dateTime = "10-08-2019 14:30:00";

    Review mockReview6 = new Review(userID, comment, rating, dateTime);

    userID = "Capybara";
    comment = "I love the sea salt brownies! I didn't know if capybaras could eat sea salt brownies, " +
            "but I just tested it and it was amazing!";
    rating = 4;
    dateTime = "20-11-2019 12:15:00";

    Review mockReview7 = new Review(userID, comment, rating, dateTime);

    mockDataList = Arrays.asList(mockReview1, mockReview2, mockReview3, mockReview4, mockReview5, mockReview6, mockReview7);
  }

  /**
   * Access mock data through this list.
   */
  public List<Review> mockDataList = new ArrayList<>();
}
