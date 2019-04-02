package com.hyk.sdk.livedatabus.life;

/**
 * Created by hyk on 2019/3/30.
 */

public interface LifecycleListener {

    void onCreate(int activityCode);
    void onStart(int activityCode);
    void onPause(int activityCode);
    void onDetach(int activityCode);

}
