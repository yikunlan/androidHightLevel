package com.hyk.sdk.pluginlib;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by hyk on 2019/3/21.
 */

public class ProxyActivity extends Activity {

    private String mClassName;

    private PluginApk mPluginApk;
    private IPlugin mIplugin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mClassName = getIntent().getStringExtra("className");
        mPluginApk = PluginManager.getInstance().getmPluginApk();

        launchanPluginActivity();
    }

    private void launchanPluginActivity() {
        if (mPluginApk == null) {
            throw new  RuntimeException("请先加载插件APK");
        }

        try {
            Class<?> clazz = mPluginApk.mDexClassLoader.loadClass(mClassName);

            //这里就是activity的实例对象，注意：这里的calzz没有生命周期，也没有上下文
            Object object = clazz.newInstance();
            if (object instanceof IPlugin) {
                mIplugin = (IPlugin)object;
                mIplugin.attach(this);

                Bundle bundle = new Bundle();
                bundle.putInt("FROM",IPlugin.FROM_EXTERNAL);
                //赋予插件生命周期
                mIplugin.onCreate(bundle);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return mPluginApk != null ? mPluginApk.mResources : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return mPluginApk != null ? mPluginApk.mAssetManager : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginApk != null ? mPluginApk.mDexClassLoader :super.getClassLoader();
    }
}
