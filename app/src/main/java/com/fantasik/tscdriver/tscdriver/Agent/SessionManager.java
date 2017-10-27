package com.fantasik.tscdriver.tscdriver.Agent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.fantasik.tscdriver.tscdriver.WelcomeActivity;

import java.util.HashMap;

/**
 * Created by a on 17-May-17.
 */

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_ID = "userid";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String driverid,String name, String username, String mobile, String pass, String image,String rate,String vehbrand,String vehcolor,String vehtypeid,String vehyear){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_ID, driverid);

        // Storing email in pref
        editor.putString(KEY_EMAIL, username);
        editor.putString("name", name);
        editor.putString("mobile", mobile);
        editor.putString("pass", pass);
        editor.putString("image", image);
        editor.putString("rate", rate);

        editor.putString("vehbrand", vehbrand);
        editor.putString("vehcolor", vehcolor);
        editor.putString("vehtypeid", vehtypeid);
        editor.putString("vehyear", vehyear);


        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, WelcomeActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public DriverDetails getDriverDetails(){

        DriverDetails driver = new DriverDetails();
        // user name
        driver.driverid = pref.getString(KEY_ID, null);
        driver.username = pref.getString(KEY_EMAIL, null);
        driver.mobile= pref.getString("mobile", null);
        driver.imgdriver = pref.getString("image", null);
        driver.name = pref.getString("name", null);
        driver.pass = pref.getString("pass",null);
        driver.rate =pref.getString("rate",null);

        driver.vehbrand =pref.getString("vehbrand",null);
        driver.vehcolor =pref.getString("vehcolor",null);
        driver.vehtypeid =pref.getString("vehtypeid",null);
        driver.vehyear =pref.getString("vehyear",null);
        // return user
        return driver;
    }

    /**
     * Clear session details
     * */
    public void logoutDriver(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, WelcomeActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
