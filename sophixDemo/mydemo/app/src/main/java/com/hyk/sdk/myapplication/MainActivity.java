package com.hyk.sdk.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.taobao.sophix.SophixManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.tv_my);
        textView.setText(textView.getText()+",版本号："+ BuildConfig.VERSION_NAME);
    }

    public void tvClick(View view) {
        //补丁
        Toast.makeText(this,"恭喜你，修复了",Toast.LENGTH_SHORT).show();
        //出错了的代码
        Toast.makeText(this,"点击出错了啊",Toast.LENGTH_SHORT).show();
    }

    public void btnClick(View view) {
        //这个在初始化的时候需要调用，去请求拉取补丁
        //文档地址：https://help.aliyun.com/document_detail/61082.html?spm=a2c4g.11186623.6.560.550671c1drZKDr
        SophixManager.getInstance().queryAndLoadNewPatch();
        Toast.makeText(this,"下载插件",Toast.LENGTH_SHORT).show();

    }
}
