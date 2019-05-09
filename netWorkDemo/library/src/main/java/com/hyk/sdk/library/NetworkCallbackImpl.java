package com.hyk.sdk.library;

import android.annotation.TargetApi;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.util.Log;

/**
 * Created by hyk on 2019/5/6.
 * 好像是订阅服务的方式
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.e(Constants.LOG_TAG,"NetworkCallbackImpl  》》》 网络已连接");
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.e(Constants.LOG_TAG,"NetworkCallbackImpl  》》》 网络已中断");
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);

        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.e(Constants.LOG_TAG,"NetworkCallbackImpl  》》》 网络变更，类型为： wifi");
            }else{
                Log.e(Constants.LOG_TAG,"NetworkCallbackImpl  》》》 网络变更，类型为： 其他");
            }
        }
    }
}
