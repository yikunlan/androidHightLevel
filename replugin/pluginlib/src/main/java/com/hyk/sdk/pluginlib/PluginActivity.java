package com.hyk.sdk.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by hyk on 2019/3/21.
 * 管理插件APP生命周期的activity
 */

public class PluginActivity extends Activity implements IPlugin {
    //定义插件的上下文
    private Activity mProxyActivity;

    private int mFrom = FROM_INTERENAL;

    /**
     *
     * @param activity
     */
    @Override
    public void attach(Activity activity) {

    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if(saveInstanceState != null){
            mFrom = saveInstanceState.getInt("FROM");
        }
        if (mFrom == FROM_INTERENAL) {
            super.onCreate(saveInstanceState);
            mProxyActivity = this;
        }
    }

    @Override
    public void setContentView(int layoutResId) {
        if (mFrom == FROM_INTERENAL) {
            super.setContentView(layoutResId);
        }else{
            mProxyActivity.setContentView(layoutResId);
        }
    }

    @Override
    public void onStart() {
        if (mFrom == FROM_INTERENAL) {
            super.onStart();
        }
    }

    @Override
    public void onRestart() {

        if (mFrom == FROM_INTERENAL) {
            super.onRestart();
        }
    }

    @Override
    public void onResume() {
        if (mFrom == FROM_INTERENAL) {
            super.onResume();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFrom == FROM_INTERENAL) {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onPause() {

        if (mFrom == FROM_INTERENAL) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (mFrom == FROM_INTERENAL) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (mFrom == FROM_INTERENAL) {
            super.onDestroy();
        }
    }
}
