package com.example.easy_glide.glide.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import com.example.easy_glide.glide.RequestBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 加载任务器
 */
public class DispatcherTask extends Thread {
    private Handler handler;
    private final LinkedBlockingQueue<RequestBuilder> requestQueue;

    public DispatcherTask(LinkedBlockingQueue<RequestBuilder> requestQueue) {
        this.requestQueue = requestQueue;
        //获取主线程的Looper，保证UI刷新
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        super.run();
        //只要线程没有中断，就一直轮训
        while (!isInterrupted()) {

            try {
                //从队列中获取请求构建对象
                RequestBuilder requestBuilder = requestQueue.take();

                //设置展位图
                placeholder(requestBuilder);
                //从网络中加载图片
                Bitmap bitmap = loadFromNet(requestBuilder);
                //最终显示图片到控件
                finalShow(requestBuilder,bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void finalShow(RequestBuilder requestBuilder, Bitmap bitmap) {
        /**
         * requestBuilder.getImageView().getTag().equals(requestBuilder.getMd5Filename()):判断tag相等，保证不会错位展示，
         * 在requestBuilder.into()的时候已经有设置image view的tag了
         */
        if (requestBuilder.getImageView() != null && bitmap != null && requestBuilder.getImageView().getTag().equals(requestBuilder.getMd5Filename())){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    requestBuilder.getImageView().setImageBitmap(bitmap);
                    if (requestBuilder.getRequestListener() != null) {
                        requestBuilder.getRequestListener().onResourceReady(bitmap);
                    }
                }
            });
        }
    }


    private Bitmap loadFromNet(RequestBuilder requestBuilder) {
        InputStream is = null;
        Bitmap bitmap = null;

        try {
            //创建url对象
            URL url = new URL(requestBuilder.getUrl());
            HttpURLConnection conne = (HttpURLConnection) url.openConnection();
            is = conne.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;
    }

    // 设置占位图
    private void placeholder(RequestBuilder requestBuilder) {
        if (requestBuilder.getPlaceId() > 0 && requestBuilder.getImageView() != null) {
            /***
             * 为什么handler的post的runnable的run方法会执行在主线程？
             * 因为post内部会把Runnable的run方法执行到handler所在的线程中，而目前的这个handler就是在主线程中，<子线程不能创建handler></>(handler的内部的方法：private static void handleCallback(Message message) {
             *         message.callback.run();
             *     })
             */
            handler.post(new Runnable() {
                @Override
                public void run() {
                    requestBuilder.getImageView().setImageResource(requestBuilder.getPlaceId());
                }
            });
        }
    }
}
