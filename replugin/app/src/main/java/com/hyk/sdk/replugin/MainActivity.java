package com.hyk.sdk.replugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hyk.sdk.pluginlib.PluginManager;
import com.hyk.sdk.pluginlib.ProxyActivity;

/**
 * Created by hyk on 2019/3/21.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        PluginManager.getInstance().init(MainActivity.this);
        findViewById(R.id.load_apk_btn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //加载apk文件
                String apkPath = Util.copyAssetAndWrite(MainActivity.this,"p4.apk");

                PluginManager.getInstance().loadApk(apkPath);


            }
        });
        findViewById(R.id.jump_activity_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到指定的activity
                Intent intent = new Intent(MainActivity.this, ProxyActivity.class);
                intent.putExtra("className","com.hyk.sdk.pluginapp.ChajianActivity");
                startActivity(intent);
            }
        });
    }
}
