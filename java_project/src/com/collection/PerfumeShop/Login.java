package com.collection.PerfumeShop;

public class Login {
    private static final String ADMIN_USERNAME = "Varshni";
    private static final String ADMIN_PASSWORD = "Varshni";
    public static boolean authenticate(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }

    public static boolean isAdmin(String username, String password) {
        return authenticate(username, password);
    }
}
