package com.hyk.sdk.library;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by hyk on 2019/3/26.
 */

public class ListenerInvocationHandler implements InvocationHandler {

    // 需要拦截MainActivity中的某些方法
    private Object target;

    //拦截的键值对
    private HashMap<String,Method> methodMap = new HashMap<>();

    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (target != null) {
            String methodName = method.getName();//加入是onclick
//            重新赋值，并且拦截了onClick方法
            method = methodMap.get(methodName);//如果找到了，就执行自定义的click方法
            if (method != null) {
                //重点理解一下
                return method.invoke(target,args);
            }
        }
        return null;
    }

    /**
     * 拦截的添加
     * @param methodName 本应该执行的方法：onClick（） ，把他拦截
     * @param method 执行的自定义的方法，比如show（），click（）
     */
    public void addMethod(String methodName,Method method){
        methodMap.put(methodName,method);
    }
}
