package com.modo.hsjx.logincomponent;

import android.app.Application;

import com.modo.hsjx.componentlibrary.IAppComponent;
import com.modo.hsjx.componentlibrary.ServiceFactory;

/**
 * Created by hyk on 2019/4/28.
 */

public class LoginApplication extends Application implements IAppComponent {

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
        application = app;
        ServiceFactory.getInstance().setLoginService(new LoginService());
    }
}
