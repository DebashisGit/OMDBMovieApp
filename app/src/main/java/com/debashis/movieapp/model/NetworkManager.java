package com.debashis.movieapp.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Debashis on 27/3/16.
 */
public class NetworkManager {

    private ConnectivityManager mConnectivityManager;
    private Context mContext;

    public NetworkManager(Context context){
        this.mContext = context;
    }

    public boolean isConnected(){
        boolean isConnected = false;

        if(mContext != null){
            mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo =  mConnectivityManager.getActiveNetworkInfo();
            isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        }

        return isConnected;
    }

    public void releaseContext(){
        mContext = null;
    }
}
