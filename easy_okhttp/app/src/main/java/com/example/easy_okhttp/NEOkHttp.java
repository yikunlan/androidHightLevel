package com.example.easy_okhttp;

import com.example.easy_okhttp.neteasyokhttp.CallbackListener;
import com.example.easy_okhttp.neteasyokhttp.HttpTask;
import com.example.easy_okhttp.neteasyokhttp.IHttpRquest;
import com.example.easy_okhttp.neteasyokhttp.IJsonDataListener;
import com.example.easy_okhttp.neteasyokhttp.JsonCallbackListener;
import com.example.easy_okhttp.neteasyokhttp.JsonHttpRequest;
import com.example.easy_okhttp.neteasyokhttp.ThreadPoolManger;

import java.io.InputStream;

public class NEOkHttp {

    public static<T,M> void sendJsonRequest(T requestData, String url, Class<M> respose, IJsonDataListener dataListener){
        IHttpRquest jsonHttpRequest = new JsonHttpRequest();
        CallbackListener callbackListener = new JsonCallbackListener<M>(respose,dataListener);
        HttpTask ht = new HttpTask(url,requestData,jsonHttpRequest,callbackListener);
        ThreadPoolManger.getInstance().addTask(ht);
    }
}
