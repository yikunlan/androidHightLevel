package com.hyk.sdk.livedatabus.livedata;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.hyk.sdk.livedatabus.life.LifecycleListener;

/**
 * Created by hyk on 2019/3/30.
 * fragment生命周期和activity是一样的e
 */

public class HolderFragment extends Fragment {

    private int activityCode;
    private LifecycleListener lifecycleListener;

    public void setLifecycleListener(LifecycleListener lifecycleListener) {
        this.lifecycleListener = lifecycleListener;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        activityCode = activity.hashCode();
        if (lifecycleListener != null) {
            lifecycleListener.onCreate(activityCode);
        }
    }

    public void onDetach() {
        super.onDetach();
        if (lifecycleListener != null) {
            lifecycleListener.onDetach(activityCode);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (lifecycleListener != null) {
            lifecycleListener.onStart(activityCode);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (lifecycleListener != null) {
            lifecycleListener.onStart(activityCode);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
