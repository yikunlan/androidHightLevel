package com.example.easy_okhttp.neteasyokhttp;

public interface IHttpRquest {

    void setUrl(String url);

    void setData(byte[] data);

    void setListener(CallbackListener listener);

    void execute();

}
