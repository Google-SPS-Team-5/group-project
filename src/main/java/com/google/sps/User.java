package com.google.sps;

import java.util.ArrayList;
import java.util.List;


public final class User {
    private String name;
    private String email;
    private List<String> favourites;
    private List<String> pastReviews;

    /**
     * Default constructor for class
     */
    public User(String userName, String userEmail, List<String> userFavourites, List<String> userPastReviews){
        name = userName;
        email = userEmail;
        favourites = userFavourites;
        pastReviews = userPastReviews;
    }
    
    @Override
    public String toString() {
        return String.format("userName: %n, userEmail: %e", name, email);
    }
}