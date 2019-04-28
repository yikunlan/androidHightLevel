package com.modo.hsjx.componentlibrary;

/**
 * Created by hyk on 2019/4/28.
 */

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance(){
        return instance;
    }

    private ServiceFactory(){}

    private ILoginService loginService;

    private IMineService mineService;

    public ILoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(ILoginService loginService) {
        this.loginService = loginService;
    }

    public IMineService getMineService() {
        return mineService;
    }

    public void setMineService(IMineService mineService) {
        this.mineService = mineService;
    }
}
