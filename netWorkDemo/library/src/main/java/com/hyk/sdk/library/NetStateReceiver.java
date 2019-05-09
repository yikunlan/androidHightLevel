package com.hyk.sdk.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hyk.sdk.library.annotation.Network;
import com.hyk.sdk.library.bean.MethodManager;
import com.hyk.sdk.library.listener.NetChangeObserver;
import com.hyk.sdk.library.type.NetType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hyk on 2019/4/15.
 */

public class NetStateReceiver extends BroadcastReceiver {

    private NetType netType;

    private NetChangeObserver listener;

    //存放添加了注解的方法
    private Map<Object , List<MethodManager>> networkList;

    public NetStateReceiver() {

        netType = NetType.NONE;
        networkList = new HashMap<>();
    }

    public void setListener(NetChangeObserver listener) {
         this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent == null || intent.getAction() == null) {
            return;
        }

        if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
            Log.e(Constants.LOG_TAG,"网络发生变化了");
            netType = NetworkUtils.getNetType();

            if(NetworkUtils.isNetWorkAvailable()){
                Log.e(Constants.LOG_TAG,"网络连接成功");
                if (listener != null) {
                    listener.onConnect(netType);
                }
            }else{
                Log.e(Constants.LOG_TAG,"没有网路连接");
                if (listener != null) {
                    listener.onDisConnect();
                }
            }

            post(netType);
        }

    }
    //消息发放到所有的activity中
    private void post(NetType netType) {
        //获取所有的activity和frgament
        Set<Object> set = networkList.keySet();
        for (Object getter : set) {
            List<MethodManager> methodList = networkList.get(getter);
            if (methodList != null) {
                for (MethodManager methodManager : methodList) {
                    //判断方法的参数类型是否和指定的netTYPE的类型一样
                    if (methodManager.getType().isAssignableFrom(netType.getClass())) {
                        switch (methodManager.getNetType()){
                            case NONE:
                                if (netType == NetType.NONE || netType ==NetType.NONE) {
                                    invoke(methodManager,getter,netType);
                                }
                                break;
                            case CMWAP:
                                if (netType == NetType.CMWAP || netType ==NetType.NONE) {
                                    invoke(methodManager,getter,netType);
                                }
                                break;
                            case CMNET:
                                if (netType == NetType.CMNET || netType ==NetType.NONE) {
                                    invoke(methodManager,getter,netType);
                                }
                                break;
                            case WIFI:
                                if (netType == NetType.WIFI || netType ==NetType.NONE) {
                                    invoke(methodManager,getter,netType);
                                }
                                break;
                            case AUTO:
                                invoke(methodManager,getter,netType);
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(MethodManager method, Object getter, NetType netType) {
        Method execute = method.getMethod();
        try {
            execute.invoke(getter,netType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerObserver(Object register) {
        //将mainActivity中所有网络注解监听的方法加入集合
        List<MethodManager> methodList = networkList.get(register);
        if(methodList == null){
            //开始添加
            methodList = findAnnotiationMethod(register);
            networkList.put(register,methodList);
        }
    }

    private List<MethodManager> findAnnotiationMethod(Object register) {
        List<MethodManager> methodList = new ArrayList<>();

        Class<?> clazz = register.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            //获取方法的注解
            Network netWork = method.getAnnotation(Network.class);
            if (netWork == null) {
                continue;
            }
            //注解方法的校验开始
            Type returnType = method.getGenericReturnType();
            if (!"void".equalsIgnoreCase(returnType.toString())) {
                //抛出异常也是可以的，这边是选择打印日志
                Log.e(Constants.LOG_TAG,method.getName()+"方法不是返回void的");
            }
//            判断参数的个数
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                //抛出异常也是可以的，这边是选择打印日志
                Log.e(Constants.LOG_TAG,method.getName()+"方法有且只有一个参数");
                throw new RuntimeException(method.getName()+"方法有且只有一个参数");
            }
            //注解方法的校验结束

            //过滤了不需要的方法，找到哦啊了开始添加到集合
            MethodManager methodManager = new MethodManager(parameterTypes[0],netWork.netType(),method);
            methodList.add(methodManager);

        }
        return null;
    }

    public void unRegisterObserver(Object register) {
        if(!networkList.isEmpty()){
            networkList.remove(register);
        }
        Log.e(Constants.LOG_TAG,register.getClass().getName()+"注销成功");
    }

    public void unRegisterAllObserver() {
        if(!networkList.isEmpty()){
            networkList.clear();
        }
        //注销广播
        NetworkManager.getDefault().getApplication().unregisterReceiver(this);
        networkList = null;
        Log.e(Constants.LOG_TAG,"注销所有监听成功");

    }
}
