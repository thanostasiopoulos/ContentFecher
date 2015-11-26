package com.bwolf.contentfetcher.helper;

/**
 * Created by bwolf on 14/11/2015.
 */


import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class SharedData {



    //appcontrol
    public int back = 0;
    public String currentFragment = "";
    public int mainrestarted = 0;
    public String userEmail = "";
    public int userID = 0;


    //vital vars
    public Activity myActivity;
    private static SharedData mInstance = null;

    //login
    public boolean is_logged = false;
    public String token_email;
    public String contact_email;
    public String current_category_id = null;
    public String current_offer_id = null;




    ////////////////////////////////////COMMON METHODS///////////////////////////////////////////////////////////////////
    //fragments - activities
    public static synchronized SharedData getInstance(Activity ctx){

        if(null == mInstance){
            mInstance = new SharedData();
            Log.e("NEW INSTANCE", "NEW COMMON DATA");
        }
        return mInstance;
    }
    
    @SuppressWarnings("deprecation")
    public Boolean NetworkExists(Activity ctx){
        myActivity = ctx;
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }

        return haveConnectedWifi || haveConnectedMobile;
    }

}
