package com.jk.simplememo;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSdk.init(this,"943d4d9e8a3804383583f42482fdb80f");
    }
}
