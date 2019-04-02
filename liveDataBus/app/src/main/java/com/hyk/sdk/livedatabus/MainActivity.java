package com.hyk.sdk.livedatabus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //构建被监听的，观察者
//        LiveDataBus.getInstance().getChannel("event",String.class).observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                Toast.makeText(MainActivity.this,"收到消息:"+s,Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        findViewById(R.id.my_tv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LiveDataBus.getInstance().getChannel("event").setValue("事件总线的消息");
//            }
//        });
    }
}
