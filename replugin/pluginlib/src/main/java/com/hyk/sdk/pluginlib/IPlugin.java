package com.hyk.sdk.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by hyk on 2019/3/21.
 */

public interface IPlugin {
    //从内部来的
    int FROM_INTERENAL = 0;
    //从外部来的
    int FROM_EXTERNAL = 1;

    //承接上下文
    void attach(Activity activity);

    void onCreate(Bundle saveInstanceState);

    void onStart();

    void onRestart();

    void onResume();

    void onActivityResult(int requestCode,int resultCode,Intent data);

    void onPause();

    void onStop();

    void onDestroy();
}
