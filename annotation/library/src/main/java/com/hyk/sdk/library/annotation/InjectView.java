package com.hyk.sdk.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hyk on 2019/3/26.
 */
@Target(ElementType.FIELD)//改注解作用在属性上
@Retention(RetentionPolicy.RUNTIME) //jvm运行时通过反射获取该注解的值
public @interface InjectView {
    int value();
}
