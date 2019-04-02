package com.hyk.sdk.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hyk on 2019/3/26.
 */
@Target(ElementType.ANNOTATION_TYPE)//元注解，作用在注解上面（注解的注解）
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    //事件的3个重要成员

    //1、setOnclickListener 方法名
    String listenerSetter();
    //2、监听对象，view.setOnclickListener
    Class<?> listenerType();
    //3、回调方法 onClick（View v)
    String callBackListener();
}
