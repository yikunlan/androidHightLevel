package com.example.easy_okhttp.neteasyokhttp;

/**
 * 纯粹做数据的传递
 */
public interface IJsonDataListener<T> {

    void onSuccess(T t);

    void onFail();
}
