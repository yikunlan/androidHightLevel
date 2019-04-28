package com.hyk.sdk.library.listener;

import com.hyk.sdk.library.tuype.NetType;

/**
 * Created by hyk on 2019/4/15.
 */

public interface NetChangeObserver {

    //网络连接时
    void onConnect(NetType type);

    //没有网络
    void onDisConnect();
}
