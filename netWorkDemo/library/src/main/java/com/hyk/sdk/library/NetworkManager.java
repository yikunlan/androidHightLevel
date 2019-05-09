package com.hyk.sdk.library;

import android.app.Application;
import android.content.IntentFilter;

import com.hyk.sdk.library.listener.NetChangeObserver;

/**
 * Created by hyk on 2019/4/16.
 */

public class NetworkManager {

    //单例
    private static volatile NetworkManager instance;

    private Application application;

    private NetStateReceiver receiver;

    private NetworkManager() {
        receiver = new NetStateReceiver();
    }

    public static NetworkManager getDefault(){
        if (instance == null) {
            synchronized ((NetworkManager.class)){
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    public void setListener(NetChangeObserver listener){
        if (receiver != null) {
            receiver.setListener(listener);
        }
    }

    public void init(Application application){
        if (application == null) {
            return;
        }
        this.application = application;

        //动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ANDROID_NET_CHANGE_ACTION);
        application.registerReceiver(receiver,filter);
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
