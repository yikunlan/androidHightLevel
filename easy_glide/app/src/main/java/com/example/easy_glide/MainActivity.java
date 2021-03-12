package com.example.easy_glide;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.example.easy_glide.glide.Glide;
import com.example.easy_glide.glide.exception.GlideException;
import com.example.easy_glide.glide.listener.RequestListener;

/***
 *
 * glide的粗糙版用来帮助理解glide的实现
 */
public class MainActivity extends AppCompatActivity {

    private String url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fa0.att.hudong.com%2F52%2F62%2F31300542679117141195629117826.jpg&refer=http%3A%2F%2Fa0.att.hudong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1618125511&t=2e5d6b8948a2a059fbe4f71ebe69a22c";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Glide.with(this)
                .load(url)
                .listener(new RequestListener() {
                    @Override
                    public void onResourceReady(Bitmap bitmap) {
                        Log.e("TAG","加载成功了呀");
                    }

                    @Override
                    public void onLoadFailed(GlideException e) {
                        Log.e("TAG","加载失败了呀");
                    }
                })
                .placeholder(R.mipmap.ic_launcher)
                .into(findViewById(R.id.iv));
    }
}