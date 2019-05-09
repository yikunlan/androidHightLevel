package com.hyk.sdk.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyk.sdk.library.listener.NetChangeObserver;
import com.hyk.sdk.library.type.NetType;

public class MainActivity extends AppCompatActivity implements NetChangeObserver{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onConnect(NetType type) {

    }

    @Override
    public void onDisConnect() {

    }
}
