package org.artisan.hiraafood.server;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

import androidx.annotation.Nullable;

public class LocalFoodService extends Service  {
    Looper serviceLooper;
    ServiceHandler serviceHandler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
    }


    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("LocalFoodService",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            try {
                Thread.sleep(5000);
                switch (msg.arg2) {
                    case GET_MENU:

                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            stopSelf(msg.arg1);
        }
    }
    public static final int GET_MENU = 0x0001;
}
