package com.saiyi.finnservicestartdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class startServices extends Service {

//    第一次启动  onCreate()----onStartCommand（）
//    关闭        onDestroy()
//    第二次启动  onCreate()----onStartCommand（）
//    第三次启动  onStartCommand（）
//    关闭        onDestroy()

    @Override
    public void onCreate() {
        Log.e("Finn","onCreate - Thread ID = " + Thread.currentThread().getId());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Finn", "onStartCommand - startId = " + startId + ", Thread ID = " + Thread.currentThread().getId());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("Finn", "onBind - Thread ID = " + Thread.currentThread().getId());
        return null;
    }

    @Override
    public void onDestroy() {
        Log.e("Finn", "onDestroy - Thread ID = " + Thread.currentThread().getId());
        super.onDestroy();
    }
}
