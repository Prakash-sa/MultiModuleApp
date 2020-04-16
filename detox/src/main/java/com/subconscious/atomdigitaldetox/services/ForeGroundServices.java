package com.subconscious.atomdigitaldetox.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.subconscious.atomdigitaldetox.helper.NotificationCountdownUtils;
import com.subconscious.atomdigitaldetox.helper.RunningPackageUtil;


import static com.subconscious.atomdigitaldetox.constants.Notifications.FOREGROUND_NOTIFICATION_ID_COUNTDOWN_START;

public class ForeGroundServices extends Service {


    private Context context;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private Integer flag=0;
    private Integer startnotify=0;
    private int flagit=0;
    @Override
    public void onCreate() {
        super.onCreate();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        context=getApplicationContext();
        handler.postDelayed(run, 1000);
        notificationstart();
        startForeground(FOREGROUND_NOTIFICATION_ID_COUNTDOWN_START, builder.build());
        return START_NOT_STICKY;
    }

    Handler handler=new Handler();
    Runnable run=new Runnable() {
        @Override
        public void run() {
            checkForAnotherPackage();
            if(startnotify==0) handler.postDelayed(run, 1000);

        }
    };

    private void checkForAnotherPackage(){
        if(RunningPackageUtil.isAnotherApplicationRunning(RunningPackageUtil.getTopPackageName(context),
                RunningPackageUtil.getLauncherPackage(context),
                getPackageName())){
            startthecountdown();
            startnotify = 1;
        }
    }

    private void notificationstart(){
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder= NotificationCountdownUtils.displayNotificationForAlert(this,notificationManager);

    }


    private void startthecountdown(){
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                builder.setContentTitle("Click here to get back the app");
                builder.setContentText("seconds remaining: " + millisUntilFinished / 1000);
                if(flagit==0){
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(800);
                }
                flagit=1;
                notificationManager.notify(FOREGROUND_NOTIFICATION_ID_COUNTDOWN_START, builder.build());
            }

            public void onFinish() {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(800);
                builder.setContentText("Your tree is dead");
                notificationManager.notify(FOREGROUND_NOTIFICATION_ID_COUNTDOWN_START, builder.build());
            }
        }.start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
