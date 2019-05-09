package com.hyk.sdk.library.type;

/**
 * Created by hyk on 2019/4/15.
 */

public enum NetType {

    //只要有网络，不关心是WiFi还是gprs
    AUTO,
    // WIFI网络
    WIFI,
    //只要是pc、笔记本电脑、pad上网
    CMNET,
    //手机上网
    CMWAP,
    //没有网络
    NONE
}
