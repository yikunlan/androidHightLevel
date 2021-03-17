package com.example.easy_okhttp.neteasyokhttp;


import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HttpTask<T> implements Runnable, Delayed {

    private IHttpRquest mHttpRquest;
    public HttpTask(String url, T requestData,IHttpRquest httpRquest, CallbackListener callbackListener){
        httpRquest.setUrl(url);
        httpRquest.setListener(callbackListener);
        try {
            String content = JSON.toJSONString(requestData);
            httpRquest.setData(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mHttpRquest = httpRquest;
    }


    @Override
    public void run() {
        try {
            mHttpRquest.execute();
        }catch (Exception e) {
            Log.e("TAGGGGG","添加11111");
            ThreadPoolManger.getInstance().addDelayTask(this);
        }
    }



    /////
    private long delayTime ;
    private int retryTime;

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis() + delayTime;
    }

    public int getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(int retryTime) {
        this.retryTime = retryTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.delayTime - System.currentTimeMillis(),TimeUnit.MICROSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }
}
