package com.example.easy_okhttp.neteasyokhttp;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 网络线程池管理类
 */
public class ThreadPoolManger {

    private static final ThreadPoolManger threadPoolManger = new ThreadPoolManger();

    public static ThreadPoolManger getInstance() {
        return threadPoolManger;
    }
    //创建队列,先进先出
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();
    //把任务添加到队列中
    public void addTask(Runnable runnable) {
        try {
            mQueue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 创建线程池
    private ThreadPoolExecutor mThreadPoolExecutor;
    public ThreadPoolManger() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        //线程池有异常的时候调用的方法
                        addTask(r);
                    }
                });
        //唤醒"叫号员"开始叫人了
        mThreadPoolExecutor.execute(coreThread);

        mThreadPoolExecutor.execute(delayThread);
    }
    //创建"叫号员"线程，让队列和线程池进行交互
    public Runnable coreThread = new Runnable() {
        Runnable runn = null;
        @Override
        public void run() {
            //一直存在，需要不停的去监听，去队列获取异步任务
            while (true) {
                try {
                    runn = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mThreadPoolExecutor.execute(runn);
            }
        }
    };

    //创建延迟队列（重试机制一般都是要延迟一定的时间才会进行重试）

    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<HttpTask>();

    public void addDelayTask(HttpTask task){
        task.setDelayTime(3000);
        mDelayQueue.offer(task);
    }

    //创建延迟线程，不断的从延迟队列中获取请求，并提交给线程池
    public Runnable delayThread = new Runnable() {
        HttpTask ht = null;
        @Override
        public void run() {
            while (true) {

                try {
                    Log.e("TAGGGGG","获取2222222");
                    ht = mDelayQueue.take();//延迟在这边，如果httpTask添加了延迟，获取的时候会有延迟
                    Log.e("TAGGGGG","获取33333333");
                    if (ht.getRetryTime() < 3){
                        Log.e("TAGGGGG","执行444444444");
                        mThreadPoolExecutor.execute(ht);
                        Log.e("TAGGGGG","执行555555");
                        ht.setRetryTime(ht.getRetryTime() + 1);
                        Log.e("TAGGGGG","重试开始："+ht.getRetryTime());
                    }else {

                        Log.e("TAGGGGG","重试结束，任务失败："+ht.getRetryTime());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
