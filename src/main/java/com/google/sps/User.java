package com.google.sps;

import java.util.ArrayList;
import java.util.List;

public final class User {
    private String name;
    private String email;
    private List<String> favourites;

    /**
     * Default constructor for class
     */
    public User(String userName, String userEmail, List<String> userFavourites){
        name = userName;
        email = userEmail;
        favourites = userFavourites;
    }
    
    @Override
    public String toString() {
        return String.format("userName: %n, userEmail: %e", name, email);
    }
}