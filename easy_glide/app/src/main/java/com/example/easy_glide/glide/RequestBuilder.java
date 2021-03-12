package com.example.easy_glide.glide;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.easy_glide.glide.listener.RequestListener;
import com.example.easy_glide.glide.utils.Md5FileNameGenerator;

import java.lang.ref.SoftReference;

/**
 * 请求构建（拼接  ）对象
 */
public class RequestBuilder {

    private final Context context;
    private String url;
    private String md5Filename;//图片文件的重新命名
    private int placeId;//占位图

    private SoftReference<ImageView> imageView;//将需要加载图片的控件放入软引用，方便回收
    private RequestListener requestListener;

    public RequestBuilder(Context context){
        this.context = context;
    }
    public RequestBuilder load(String url){
        this.url = url;
        //该文件名，方便存储本地文件取名,避免乱码问题
        this.md5Filename = Md5FileNameGenerator.generate(url);
        return this;
    }

    public RequestBuilder placeholder(int placeId){
        this.placeId = placeId;
        return this;
    }

    //参数的校验和和初始化
    public void into(ImageView imageView){
        //设置mark（tag）
        imageView.setTag(md5Filename);
        this.imageView = new SoftReference<>(imageView);

        if (TextUtils.isEmpty(url)){
            //抛出自定义的GlideException异常
        }

        if (placeId <= 0) {
            //do what you want
        }
        // 将包装好的请求对象体，添加到请求管理类的队列中
        RequestManager.getInstance().addRequestQueue(this).dispatcher();
    }

    public Context getContext() {
        return context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5Filename() {
        return md5Filename;
    }

    public void setMd5Filename(String md5Filename) {
        this.md5Filename = md5Filename;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public void setImageView(SoftReference<ImageView> imageView) {
        this.imageView = imageView;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public RequestBuilder listener(RequestListener requestListener) {
        this.requestListener = requestListener;
        return this;
    }
}
