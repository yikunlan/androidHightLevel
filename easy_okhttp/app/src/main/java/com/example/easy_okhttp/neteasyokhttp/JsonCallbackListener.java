package com.example.easy_okhttp.neteasyokhttp;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonCallbackListener<T> implements CallbackListener {
    private final IJsonDataListener mDataListener;
    private Class<T> resposeClass;

    public JsonCallbackListener(Class<T> resposeClass, IJsonDataListener dataListener) {
        this.resposeClass = resposeClass;
        this.mDataListener = dataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        String content = getContent(inputStream);
        if (content.length() > 0) {
//                content = "{\"resultcode\":\"101\",\"reason\":\"错误的请求KEY\",\"result\":null,\"error_code\":10001}";
            T t = JSON.parseObject(content, resposeClass);
            mDataListener.onSuccess(t);
        } else {
            mDataListener.onFail();
        }
    }

    @Override
    public void onFailure() {

    }

    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }
}
