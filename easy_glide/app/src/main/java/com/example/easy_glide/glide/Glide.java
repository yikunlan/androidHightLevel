package com.example.easy_glide.glide;

import android.content.Context;

public class Glide {

    //初始化管理器
    public static RequestManager with(Context context){
        return RequestManager.getInstance().get(context);
    }
}
