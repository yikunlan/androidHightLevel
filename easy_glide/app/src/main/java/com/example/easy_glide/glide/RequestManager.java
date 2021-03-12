package com.example.easy_glide.glide;

import android.content.Context;

import com.example.easy_glide.glide.task.DispatcherTask;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 请求管理类
 */
public class RequestManager {

    private static RequestManager instance;
    private Context context;
    private String url;
    //创建一个队列，先进先出,从头部开始获取数据，新增的数据哦都市放置在末尾，并发时保障线程安全
    private LinkedBlockingQueue<RequestBuilder> requestQueue = new LinkedBlockingQueue<>();

    //加载任务器
    private DispatcherTask[] tasks;

    // 在glide4.9.0源码中，没有单例，而是通过factoru创建的
    public static RequestManager getInstance() {
        synchronized (RequestManager.class) {
            if (instance == null) {
                instance = new RequestManager();
            }
        }

        return instance;
    }

    private RequestManager() {
        // 在glide4.9.0源码中，直接显示错误error，并且初始化了placeholder占位图
    }

    public RequestManager get(Context context) {
        this.context = context;
        return this;
    }

    public RequestBuilder load(String url) {
        return new RequestBuilder(context).load(url);
    }

    //需要加载的请求对象，放入队列中
    public RequestManager addRequestQueue(RequestBuilder requestBuilder) {
        //有没有重复的请求对象
        if (!requestQueue.contains(requestBuilder)) {
            requestQueue.add(requestBuilder);
        }
        return this;
    }

    //触发工作，真正去执行任务
    public void dispatcher() {
        //获取虚拟机可用的最大处理器的数量，不小于一个
        int threadCount = Runtime.getRuntime().availableProcessors();
        tasks = new DispatcherTask[threadCount];

        for (int i = 0; i < threadCount; i++) {
            DispatcherTask task = new DispatcherTask(requestQueue);
            task.start();
            tasks[i] = task;
        }
    }

    //供给外部调用，用来停止任务的
    public void stop() {
        if (tasks.length > 0) {
            for (DispatcherTask task : tasks) {
                //如果线程任务没有中断，那么去中断任务
                if (!task.isInterrupted()) {
                    task.interrupt();
                }
            }
        }
    }
}
