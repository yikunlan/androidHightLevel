package com.hyk.sdk.library.annotation;

import com.hyk.sdk.library.type.NetType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hyk on 2019/5/6.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Network {

    NetType netType() default NetType.NONE;//默认返回没有网络连接的状态

}
