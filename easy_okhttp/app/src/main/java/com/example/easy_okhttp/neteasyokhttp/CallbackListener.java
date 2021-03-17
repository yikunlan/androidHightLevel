package com.example.easy_okhttp.neteasyokhttp;

import java.io.InputStream;

public interface CallbackListener {

    void onSuccess(InputStream inputStream);

    void onFailure();


}
