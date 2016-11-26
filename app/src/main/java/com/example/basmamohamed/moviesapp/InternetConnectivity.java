package com.example.basmamohamed.moviesapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Basma Mohamed on 11/26/2016.
 */

public class InternetConnectivity {

    private Context mcontext;



    public InternetConnectivity(Context context){
        this.mcontext = context;
    }

    public boolean isConnected(){
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
