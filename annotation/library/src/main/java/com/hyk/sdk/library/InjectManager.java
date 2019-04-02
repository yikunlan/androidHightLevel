package com.hyk.sdk.library;

import android.app.Activity;
import android.view.View;

import com.hyk.sdk.library.annotation.ContentView;
import com.hyk.sdk.library.annotation.EventBase;
import com.hyk.sdk.library.annotation.InjectView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by hyk on 2019/3/26.
 */

public class InjectManager {

    public static void inject(Activity activity){
        //布局注入
        injectLayout(activity);
        //控件注入
        injectViews(activity);
        //事件注入
        injectEvents(activity);
    }

    //布局注入
    private static void injectLayout(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取类的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);

        if(contentView != null){
            //获取到了布局的值（R.layout.activity_main）
            int layoutId = contentView.value();
            //第一种方式：
//            activity.setContentView(layoutId);
            //第二种通过反射的方式来设置布局
            try {
                //获取父类的方法：setContentView
                Method method = clazz.getMethod("setContentView", int.class);
                //（invoke执行）执行activity的setContentView方法，传入的参数为layoutId
                method.invoke(activity,layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //控件注入
    private static void injectViews(Activity activity) {

        Class<? extends Activity> clazz = activity.getClass();
        //得到正在使用的activity的所有属性
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            //得到属性的注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            //并不是所有的属性都是有注解的，比如定义的常亮啊非控件类的变量之类的
            if(injectView != null){
                int viewId = injectView.value();
                //第一种方式
//                activity.findViewById(viewId);
                //第二种方式，通过反射
                try {
                    Method method = clazz.getMethod("findViewById", int.class);
                    Object view = method.invoke(activity, viewId);

                    field.setAccessible(true);//设置改属性为可访问的，哪怕是private修饰的
                    //把activity对象的属性field的值改变为view
                    field.set(activity,view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //事件注入
    private static void injectEvents(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        //获取当前的方法
        Method[] methods = clazz.getDeclaredMethods();
        //遍历方法
        for (Method method : methods) {
            //获取每个方法的注解，一个方法可能会有多个注解
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    //事件的3个重要成员
                    String listenerSetter = eventBase.listenerSetter();
                    String callBackListener = eventBase.callBackListener();
                    Class<?> listenerType = eventBase.listenerType();

                    try {
                        //通过annotationType获取onClick注解的value值，拿到R.id.xxxx
                        Method valueMethod = annotationType.getDeclaredMethod("value");
                        //R.id.tv，R.id.btn
                        int[] viewIds = (int[]) valueMethod.invoke(annotation);

                        //拦截方法，执行自定义方法,把activity传进去，对activity的某些方法进行拦截
                        ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                        //拦截掉方法，执行自定义的方法
                        handler.addMethod(callBackListener,method);
                        //代理方式完成
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);
                        for (int viewId : viewIds) {
                            //获取当前的view
                            View view = activity.findViewById(viewId);
                            //获取方法
                            Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                            setter.invoke(view,listener);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
