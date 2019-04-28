package com.hyk.sdk.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hyk.sdk.library.listener.NetChangeObserver;
import com.hyk.sdk.library.tuype.NetType;

/**
 * Created by hyk on 2019/4/15.
 */

public class NetStateReceiver extends BroadcastReceiver {

    private NetType netType;

    private NetChangeObserver netChangeObserver;

    public void setNetChangeObserver(NetChangeObserver netChangeObserver) {
         this.netChangeObserver = netChangeObserver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
