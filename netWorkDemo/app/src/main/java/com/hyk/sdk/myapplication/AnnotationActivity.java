package com.hyk.sdk.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hyk.sdk.library.Constants;
import com.hyk.sdk.library.NetworkManager;
import com.hyk.sdk.library.annotation.Network;
import com.hyk.sdk.library.type.NetType;

/**
 * Created by hyk on 2019/5/6.
 */

public class AnnotationActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkManager.getDefault().register(this);
    }

    //要配合application中的初始化
    @Network(netType = NetType.WIFI)
    private void netWork(NetType netType){
        switch (netType){
            case WIFI:
                Log.i(Constants.LOG_TAG,"WIFI 连接");
                break;
            case CMNET:
            case CMWAP:
                Log.i(Constants.LOG_TAG,"流量 连接");
                break;

            case NONE:
                Log.i(Constants.LOG_TAG,"没有网络 连接");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getDefault().unRegister(this);
        NetworkManager.getDefault().unRegisterAll();
    }
}
