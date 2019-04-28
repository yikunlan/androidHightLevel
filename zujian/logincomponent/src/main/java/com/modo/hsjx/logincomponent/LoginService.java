package com.modo.hsjx.logincomponent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.modo.hsjx.componentlibrary.ILoginService;

/**
 * Created by hyk on 2019/4/28.
 */

public class LoginService implements ILoginService {
    public static final String EXTRA_TARGET_CLASS = "extra_target_class";

    @Override
    public void launch(Context context, String targetClass) {
        Intent intent  = new Intent(context,LoginActivity.class);
        intent.putExtra(EXTRA_TARGET_CLASS,targetClass);
        context.startActivity(intent);
    }

    @Override
    public Fragment newUserInfoFragment(FragmentManager fragmentManager, int viewId, Bundle bundle) {
        UserinfoFragment fragment = new UserinfoFragment();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().add(viewId,fragment).commit();
        return fragment;
    }
}
