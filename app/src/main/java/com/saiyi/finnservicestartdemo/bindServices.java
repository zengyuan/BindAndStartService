package com.saiyi.finnservicestartdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class bindServices extends Service {


//    第一次启动  onCreate()----onBind（）
//    关闭       onUnbind（）----- onDestroy()
//    第二次启动  onCreate()----onBind（）
//    关闭       onUnbind（）----- onDestroy()
//    第三次启动  onCreate()----onBind（）
//    关闭       onUnbind（）----- onDestroy()


    //client 可以通过Binder获取Service实例
    public class MyBinder extends Binder {
        public bindServices getService() {
            return bindServices.this;
        }
    }

    //通过binder实现调用者client与Service之间的通信
    private MyBinder binder = new MyBinder();

    private final Random generator = new Random();
    @Override
    public void onCreate() {
        Log.e("FinnZy", "bindServices - onCreate - Thread = " + Thread.currentThread().getName());
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("FinnZy", "bindServices - onStartCommand - startId = " + startId + ", Thread = " + Thread.currentThread().getName());
//   reture 返回值有四种了类型
//（1）START_STICKY：如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建service，由于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传递到service，那么参数Intent将为null。
//（2）START_NOT_STICKY：“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统将会把它置为started状态，系统不会自动重启该服务，直到startService(Intent intent)方法再次被调用;。
//（3）START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
//（4）START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。
//        可能有人会问，那我把任务完成之后，该怎样关闭Service？ 可以通过：stopSelf();自己关闭；
        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("FinnZy", "TestTwoService - onBind - Thread = " + Thread.currentThread().getName());
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("FinnZy", "bindServices - onUnbind - from = " + intent.getStringExtra("from"));
        return false;
    }

    @Override
    public void onDestroy() {
        Log.e("FinnZy", "bindServices - onDestroy - Thread = " + Thread.currentThread().getName());
        super.onDestroy();
    }

    //getRandomNumber是Service暴露出去供client调用的公共方法
    public int getRandomNumber() {
        return generator.nextInt();
    }
}