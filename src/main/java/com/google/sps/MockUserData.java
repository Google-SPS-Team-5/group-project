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
        
        User user1 = new User(name, email, favourites);

        name = "Wendy";
        email = "wendy@gmail.com";
        favourites = Arrays.asList("1","2","3","4");
        
        User user2 = new User(name, email, favourites);

        name = "Tiffany";
        email = "tiffany@gmail.com";
        favourites = Arrays.asList("1","2","3","4");
        
        User user3 = new User(name, email, favourites);

        mockUserDataList = Arrays.asList(user1, user2, user3);
    }

    public List<User> mockUserDataList = new ArrayList<User>();
}