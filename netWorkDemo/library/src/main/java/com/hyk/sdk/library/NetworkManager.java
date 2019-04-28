package com.hyk.sdk.library;

import android.app.Application;

/**
 * Created by hyk on 2019/4/16.
 */

public class NetworkManager {

    //单例
    private static volatile NetworkManager instance;

    private Application application;

    private NetStateReceiver receiver;

    private NetworkManager() {

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

    public Application getApplication() {
        return application;
    }
}
