package com.hyk.sdk.pluginlib;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * Created by hyk on 2019/3/21.
 */

public class PluginApk {

    //插件apk的实体对象
    public PackageInfo mPackageInfo;

    public Resources mResources;

    public AssetManager mAssetManager;

    public DexClassLoader mDexClassLoader;

    public PluginApk(PackageInfo mPackageInfo, Resources mResources, DexClassLoader mDexClassLoader) {
        this.mPackageInfo = mPackageInfo;
        this.mResources = mResources;
        this.mAssetManager = mResources.getAssets();
        this.mDexClassLoader = mDexClassLoader;
    }
}
