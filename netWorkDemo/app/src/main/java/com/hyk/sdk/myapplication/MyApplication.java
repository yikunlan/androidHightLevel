package com.hyk.sdk.myapplication;

import android.app.Application;

import com.hyk.sdk.library.NetworkManager;
import com.hyk.sdk.library.NetworkManager2;

/**
 * Created by hyk on 2019/5/6.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        NetworkManager.getDefault().init(this);
        NetworkManager2.getDefault().init(this);
    }
}
