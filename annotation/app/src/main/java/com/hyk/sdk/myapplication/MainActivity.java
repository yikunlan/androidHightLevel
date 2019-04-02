package com.hyk.sdk.myapplication;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hyk.sdk.library.InjectManager;
import com.hyk.sdk.library.annotation.ContentView;
import com.hyk.sdk.library.annotation.InjectView;
import com.hyk.sdk.library.annotation.Onclick;

/**
 * 自己创建的注解，等价于实现了onCreate里面的
 setContentView(R.layout.activity_main);
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.my_text)
    TextView myTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,myTextView.getText().toString(),Toast.LENGTH_SHORT).show();
    }
    @Onclick({R.id.my_text})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.my_text:
                Toast.makeText(MainActivity.this,"我呗点击了啊",Toast.LENGTH_SHORT).show();
        }
    }
    @TargetApi(value = 11)
    public void dd(){

    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
}
