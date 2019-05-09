package com.hyk.sdk.library;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

import com.hyk.sdk.library.type.NetType;

/**
 * Created by hyk on 2019/5/5.
 */

public class NetworkUtils {
    @SuppressLint("MissingPermission")
    public static NetType getNetType(){
        ConnectivityManager connMgr = (ConnectivityManager) NetworkManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr == null) {
            return NetType.NONE;
        }

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }
        int nType = networkInfo.getType();

        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                return NetType.CMNET;
            }else{
                return NetType.CMWAP;
            }
        }else if(nType == ConnectivityManager.TYPE_WIFI){
            return NetType.WIFI;
        }
        return NetType.NONE;


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean isNetWorkAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager) NetworkManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr == null) {
            return false;
        }
        NetworkInfo[] info = connMgr.getAllNetworkInfo();
        if (info != null) {
            for (NetworkInfo anInfo : info) {
                if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
