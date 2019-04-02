package com.hyk.sdk.livedatabus.livedata;

import android.support.annotation.Nullable;

/**
 * Created by hyk on 2019/3/30.
 */

public abstract class Observer<T> {

    /**
     * 活跃状态
     */
    static final int STATE_ACTIVE = 1;
    /**
     * 暂停状态
     */
    static final int STATE_ONPAUSE = 2;
    /**
     * 初始化状态
     */
    static final int STATE_INIT = 0;
    /**
     * 销毁状态
     */
    static final int STATE_DESTORY = 3;

    private int state = STATE_ACTIVE;
    //当前的组件状态
    public abstract void onChanged(@Nullable T t);


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
