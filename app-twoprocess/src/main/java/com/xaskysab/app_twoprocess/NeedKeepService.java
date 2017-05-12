package com.xaskysab.app_twoprocess;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class NeedKeepService extends Service {


    private ServiceConnection conn;
    private IBinder binder ;

    @Override
    public void onCreate() {
        super.onCreate();

        binder = new MyBinder();

        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                Log.i("MyLog", "remoteService is connect");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                Log.i("MyLog", "remoteService is killed");


                Intent remoteService = new Intent(NeedKeepService.this,RemoteService.class);
                startService(remoteService);
                bindService(remoteService,conn,BIND_IMPORTANT);


            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Intent remoteService = new Intent(this,RemoteService.class);

        bindService(remoteService,conn, BIND_IMPORTANT);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    class MyBinder extends IProcessAidl.Stub{


        @Override
        public String getProcessName() throws RemoteException {

            return "Local Service";
        }
    }


}
