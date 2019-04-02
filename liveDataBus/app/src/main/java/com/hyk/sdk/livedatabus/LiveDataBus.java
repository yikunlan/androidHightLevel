package com.hyk.sdk.livedatabus;

import android.arch.lifecycle.MutableLiveData;

import com.hyk.sdk.livedatabus.livedata.LiveData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hyk on 2019/3/30.
 * 事件总线框架
 */

public class LiveDataBus {
    //消息通道
    private Map<String,LiveData<Object>> bus;

    private static LiveDataBus instance ;

    private LiveDataBus() {
        this.bus = new HashMap<>();
    }

    public static LiveDataBus getInstance() {
        return instance = instance == null?new LiveDataBus():instance;
    }

    public <T> LiveData<T> getChannel(String target,Class<T> type){
        if (!bus.containsKey(target)) {
            bus.put(target,new LiveData<Object>());
        }
        return (LiveData<T>) bus.get(target);
    }

    public <T> LiveData<T> getChannel(String target){
        return (LiveData<T>) getChannel(target,Object.class);
    }





}
