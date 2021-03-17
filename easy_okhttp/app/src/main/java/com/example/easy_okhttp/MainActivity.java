package com.example.easy_okhttp;

/**
 * okhttp的原理解析
 * 主要是线程池和队列的使用
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.easy_okhttp.neteasyokhttp.IJsonDataListener;

public class MainActivity extends AppCompatActivity {

    private String url = "http://v.juhe.cn/historyWeather/citys?province_id=2&key=bb5218880";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendRequest();
    }

    private void sendRequest() {
        NEOkHttp.sendJsonRequest(null, url, ResposeResult.class, new IJsonDataListener<ResposeResult>() {
            @Override
            public void onSuccess(ResposeResult resposeResult) {
                Log.e("TAGGGGGG",resposeResult.toString());
            }

            @Override
            public void onFail() {

            }
        });
    }
}