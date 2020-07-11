package com.google.sps;

import com.google.sps.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class MockUserData {
    public MockUserData(){
        String name = "Sam";
        String email = "sam@gmail.com";
        List<String> favourites = Arrays.asList("1","2","3","4");
        List<String> pastReviews = Arrays.asList("5","6","7","8");
        
        User user1 = new User(name, email, favourites, pastReviews);

        name = "Wendy";
        email = "wendy@gmail.com";
        favourites = Arrays.asList("1","2","3","4");
        pastReviews = Arrays.asList("5","6","7","8");
        
        User user2 = new User(name, email, favourites, pastReviews);

        name = "Tiffany";
        email = "tiffany@gmail.com";
        favourites = Arrays.asList("1","2","3","4");
        pastReviews = Arrays.asList("5","6","7","8");
        
        User user3 = new User(name, email, favourites, pastReviews);

        mockUserDataList = Arrays.asList(user1, user2, user3);
    }

    public List<User> mockUserDataList = new ArrayList<User>();
}