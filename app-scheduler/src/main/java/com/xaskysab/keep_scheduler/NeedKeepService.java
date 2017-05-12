package com.xaskysab.keep_scheduler;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NeedKeepService extends Service {

    public NeedKeepService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("--- keep service alive ---");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("--- keep service dead --- ");
    }
}
