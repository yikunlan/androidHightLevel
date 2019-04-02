package com.hyk.sdk.livedatabus.livedata;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Looper;

import com.hyk.sdk.livedatabus.life.LifecycleListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hyk on 2019/3/30.
 */

public class LiveData<T> {

    //组件地址集合
    private HashMap<Integer,Observer<T>> map = new HashMap<>();

    Handler handler = new Handler(Looper.getMainLooper());

    //缓冲消息棒？延迟集合，如果activity不活跃的时候收到了推送就要放入这里，当activity活跃的时候在推送给他
    private HashMap<Integer,List<T>> mPendingDelayList = new HashMap<>();
    /**
     *切换线程
     * 调用的时候有可能在其他的线程，需要切换到主线程
     */
    public void postValue(final T value){
        synchronized (this){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    setValue(value);
                }
            });
        }
    }

    public void setValue(T value){
        List<Observer> destroyList = new ArrayList<>();
        for (Map.Entry<Integer, Observer<T>> entry : map.entrySet()) {
            Observer<T> observer = entry.getValue();
            Integer activityCode = entry.getKey();
            //活跃的状态下正常接收
            if (observer.getState() == Observer.STATE_ACTIVE) {
                observer.onChanged(value);
            }
            //不活跃的话，先保存起来，当他获取的时候重新获取
            if(observer.getState() == Observer.STATE_ONPAUSE){
                if (mPendingDelayList.get(activityCode) == null) {
                    mPendingDelayList.put(activityCode,new ArrayList<T>());
                }
                //不在缓冲池中的才添加进去
                if (!mPendingDelayList.get(activityCode).contains(value)) {
                    mPendingDelayList.get(activityCode).add(value);
                }
            }
            if(observer.getState() == Observer.STATE_DESTORY){
                destroyList.add(observer);
            }

        }
    }

    public void observe(Activity activity,Observer<T> observer) {
        FragmentManager fm = activity.getFragmentManager();

        //查找fragment
        HolderFragment current = (HolderFragment) fm.findFragmentByTag("com.hyk.sdk.livedatabus.livedata");
        //如果没有的话就添加进去
        if (current == null) {
            current = new HolderFragment();
            fm.beginTransaction().add(current,"com.hyk.sdk.livedatabus.livedata").commitAllowingStateLoss();
        }

        current.setLifecycleListener(lifecycleListener);
        map.put(activity.hashCode(),observer);
    }

    private LifecycleListener lifecycleListener = new LifecycleListener() {
        @Override
        public void onCreate(int activityCode) {
            //可以感知activity的生命周期，和activity的生命周期同步的
            map.get(activityCode).setState(Observer.STATE_INIT);
        }

        @Override
        public void onStart(int activityCode) {
            map.get(activityCode).setState(Observer.STATE_ACTIVE);

            if ((mPendingDelayList.get(activityCode)  == null || mPendingDelayList.get(activityCode).size() == 0)) {
                return;
            }

            for (T t : mPendingDelayList.get(activityCode)) {
                map.get(activityCode).onChanged(t);
            }
            mPendingDelayList.get(activityCode).clear();
        }

        @Override
        public void onPause(int activityCode) {
            map.get(activityCode).setState(Observer.STATE_ONPAUSE);

        }

        @Override
        public void onDetach(int activityCode) {
            map.remove(activityCode);

        }
    };
}
