package com.example.easy_glide.glide.listener;

import android.graphics.Bitmap;

import com.example.easy_glide.glide.exception.GlideException;

/**
 * 图片家在监听接口
 */
public interface RequestListener {

    //资源准备好了
    void onResourceReady(Bitmap bitmap);

    //加载出现异常
    void onLoadFailed(GlideException e);
}
