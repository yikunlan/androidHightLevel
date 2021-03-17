package com.example.easy_okhttp.neteasyokhttp;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonHttpRequest implements IHttpRquest {
    private String mUrl;
    private byte[] mData;
    private CallbackListener mCallbackListener;
    private HttpURLConnection urlConnection;

    @Override
    public void setUrl(String url) {
        this.mUrl = url;
    }

    @Override
    public void setData(byte[] data) {
        mData = data;
    }

    @Override
    public void setListener(CallbackListener listener) {
        this.mCallbackListener = listener;
    }

    /**
     * 任务的具体操作
     */
    @Override
    public void execute() {
        //访问网络的具体操作
        URL url =null;

        try {
            url =new URL(this.mUrl);
            urlConnection = (HttpURLConnection) url.openConnection();//打开http链接
            urlConnection.setConnectTimeout(6000);//链接的超时时间
            urlConnection.setUseCaches(false);//不使用缓存
            urlConnection.setInstanceFollowRedirects(true);//是成员函数，金作用于当前函数，设置这个链接是否可以被重定向
            urlConnection.setReadTimeout(3000);//响应的超时时间
            urlConnection.setDoInput(true);//设置这个连接是否可以输入数据
            urlConnection.setDoOutput(true);//设置这个连接是否可以输出数据
            urlConnection.setRequestMethod("POST");//
            urlConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8");//设置消息的类型
            urlConnection.connect();//开始连接，从上述至此的配置必须要在connect之前完成，实际上它只是建立了一个与服务器的TCP连接
            //-------使用字节流发送数据 ------//
            OutputStream out = urlConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);
            bos.write(mData);
            bos.flush();
            bos.close();
            out.close();
            //---------字符流写入数据 ---//
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {//得到服务器的返回吗是否连接成功
                InputStream in = urlConnection.getInputStream();
                mCallbackListener.onSuccess(in);
            }else {
                throw new RuntimeException("请求失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("请求失败");
        }finally {
            urlConnection.disconnect();
        }
    }
}
