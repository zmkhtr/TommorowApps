package com.sahabatpnj.tommorowapps.Database;

import com.orhanobut.hawk.Hawk;
import com.sahabatpnj.tommorowapps.Model.User;

public class UserManager {

    private static final String SESSION_KEY = "sessionKey"; // KEY for get Session Value
    private static final String EMAIL_KEY = "emailKey";

    public static boolean registerNewUser(String userEmail, User user){
        return Hawk.put(userEmail,user);
    } // Function for registering, email use as userEmail

    public static User getUserData(String userEmail){
        return Hawk.get(userEmail, new User("#","#","#"));
    } // Function for get logged in user data by email as userEmail

    public static Boolean loginExistingUser(String userEmail){
        return Hawk.get(userEmail, false);
    } // Function for logged-in existing user

    public static boolean isUserAlreadyRegistered(String userEmail){
        return Hawk.contains(userEmail);
    } // Function for check if user already registered ? email as userEmail

    public static void setUserLoginSession(boolean isLogin, String userEmail){
        Hawk.put(SESSION_KEY, isLogin);
        Hawk.put(EMAIL_KEY, userEmail);
    } // Function for set user session

    public static boolean isUserLoggedIn(){
        return Hawk.get(SESSION_KEY, false);
    } // Function for checking user session

    public static String getUserEmail(){
        return Hawk.get(EMAIL_KEY, "#");
    } // Function for getting logged user email

    public static void logoutUser(){
        Hawk.delete(SESSION_KEY);
        Hawk.delete(EMAIL_KEY);
    } // Function for logging out user
}
