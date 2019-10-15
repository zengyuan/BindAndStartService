package com.saiyi.finnservicestartdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


//    Android Service启动方式和启动周期

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        start();
//        stopService();
        bind();
    }

    private void start(){
        //连续启动Service
        Intent intentOne = new Intent(this, startServices.class);
        startService(intentOne);
        Intent intentTwo = new Intent(this, startServices.class);
        startService(intentTwo);
        Intent intentThree = new Intent(this, startServices.class);
        startService(intentThree);

        //停止Service
        Intent intentFour = new Intent(this, startServices.class);
        stopService(intentFour);//不管启动多少次，只需要发一次stopservice停止service就会停止

        //再次启动Service
        Intent intentFive = new Intent(this, startServices.class);
        startService(intentFive);

        Log.e("Finn", "after StartService");
    }
    private void stopService(){
        //停止Service
        Intent intentFour = new Intent(this, startServices.class);
        stopService(intentFour);//不管启动多少次，只需要发一次stopservice停止service就会停止
    }


    private void bind(){
        Intent intent = new Intent(this, bindServices.class);
        intent.putExtra("from", "ActivityA");

        bindService(intent, conn, BIND_AUTO_CREATE);
        Log.e("Finn", "after bindService");
    }
    private void unbind(){
        if (isBind) {//绑定成功 解绑
            Log.i("Finn", "ActivityA 执行 unbindService");
            unbindService(conn);
        }
    }

    private bindServices service = null;
    private boolean isBind = false;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            isBind = true;
            bindServices.MyBinder myBinder = (bindServices.MyBinder) binder;
            service = myBinder.getService();
            int num = service.getRandomNumber();
            Log.i("Finn", "ActivityA - getRandomNumber = " + num);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
            Log.i("Finn", "ActivityA - onServiceDisconnected");
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unbind();
    }
}
