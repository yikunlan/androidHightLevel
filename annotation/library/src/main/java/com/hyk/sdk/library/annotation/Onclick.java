package com.hyk.sdk.library.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hyk on 2019/3/26.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnClickListener",listenerType = View.OnClickListener.class,callBackListener = "onClick")
public @interface Onclick {
    int[] value();
}
