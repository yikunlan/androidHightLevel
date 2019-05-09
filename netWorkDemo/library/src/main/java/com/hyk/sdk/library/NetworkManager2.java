package com.hyk.sdk.library;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import com.hyk.sdk.library.annotation.Network;
import com.hyk.sdk.library.listener.NetChangeObserver;

/**
 * Created by hyk on 2019/4/16.
 * 不使用receive的方式，使用服务的方式
 */

public class NetworkManager2 {

    //单例
    private static volatile NetworkManager2 instance;

    private Application application;

    private NetStateReceiver receiver;

    private NetworkManager2() {
        receiver = new NetStateReceiver();
    }

    public static NetworkManager2 getDefault(){
        if (instance == null) {
            synchronized ((NetworkManager2.class)){
                if (instance == null) {
                    instance = new NetworkManager2();
                }
            }
        }
        return instance;
    }


    public void init(Application application){
        if (application == null) {
            return;
        }
        this.application = application;

        //动态注册广播
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
//        application.registerReceiver(receiver,filter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager.NetworkCallback networkCallback = new NetworkCallbackImpl();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            ConnectivityManager cmgr = (ConnectivityManager) NetworkManager2.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cmgr != null) {
                cmgr.registerNetworkCallback(request,networkCallback);
            }
        }
    }


    public Application getApplication() {
        if (application == null) {
            throw new RuntimeException("xxxxx init()");
        }

        return application;
    }

    /**
     *
     * @param register 传object的原因是有可能是activity也有可能是fragment
     */
    public void register(Object register) {
        receiver.registerObserver(register);
    }

    public void unRegister(Object register) {
        receiver.unRegisterObserver(register);
    }

    public void unRegisterAll() {
        receiver.unRegisterAllObserver();
    }
}
