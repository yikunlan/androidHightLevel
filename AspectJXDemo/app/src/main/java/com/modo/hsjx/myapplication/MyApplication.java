package com.modo.hsjx.myapplication;

import android.app.Application;

import com.firefly1126.permissionaspect.PermissionCheckSDK;

/**
 * Created by hyk on 2019/5/6.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //aspectjx申请动态权限
        PermissionCheckSDK.init(this);
    }
}
