package com.modo.hsjx.myapplication;

import android.app.Application;

import com.modo.hsjx.componentlibrary.AppConfig;
import com.modo.hsjx.componentlibrary.IAppComponent;

/**
 * Created by hyk on 2019/4/28.
 */

public class MainApplication extends Application implements IAppComponent{


    private static Application application;

    public static  Application getApplication(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializa(this);
    }

    @Override
    public void initializa(Application app) {
        //将主app的上下文传到login记忆mine application中
        for(String component: AppConfig.COMPONENTS){
            try {
                Class<?> clazz = Class.forName(component);
                Object object = clazz.newInstance();
                if (object instanceof IAppComponent) {
                    ((IAppComponent)object).initializa(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
