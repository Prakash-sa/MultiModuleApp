package com.subconscious.atomdigitaldetox.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.subconscious.atomdigitaldetox.MainActivity;
import com.subconscious.atomdigitaldetox.R;

import java.util.Random;

import static com.subconscious.atomdigitaldetox.constants.Notifications.FOREGROUND_NOTIFICATION_CHANNEL_ID_COUNTDOWN_START;
import static com.subconscious.atomdigitaldetox.constants.Notifications.NOTIFICATION_CHANNEL_ID_COUNTDOWN_START;
import static com.subconscious.atomdigitaldetox.constants.Notifications.NOTIFICATION_ID_COUNTDOWN_START;

public class NotificationCountdownUtils {


    public static NotificationCompat.Builder displayNotificationForTree(Context context, NotificationManager notificationManager){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setOngoing(true)
                .setContentTitle("Your tree is still growing...")
                .setContentText("Time is ")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(new NotificationChannel(NOTIFICATION_CHANNEL_ID_COUNTDOWN_START, "Notification", NotificationManager.IMPORTANCE_HIGH));
            builder.setChannelId(NOTIFICATION_CHANNEL_ID_COUNTDOWN_START);
        }
        notificationManager.notify(NOTIFICATION_ID_COUNTDOWN_START , builder.build());
        return builder;
    }

    public static void startCountDown(final NotificationManager notificationManager, final NotificationCompat.Builder builder, final Context context, final int id,int countDownInMillis){
        new CountDownTimer(countDownInMillis, 1000) {

            public void onTick(long millisUntilFinished) {
                builder.setContentTitle("Your tree is planting ");
                builder.setContentText("seconds remaining: " + millisUntilFinished / 1000);
                notificationManager.notify(id, builder.build());
            }

            public void onFinish() {
                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(800);
                builder.setContentText("Tree planted");
                notificationManager.notify(id, builder.build());
            }
        }.start();

    }


    public static NotificationCompat.Builder displayNotificationForAlert(Context context, NotificationManager notificationManager){
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, new Random().nextInt(), intent1, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setOngoing(true)
                .setContentTitle("Your tree is still growing...")
                .setContentText("Time is ")
                .setContentIntent(contentIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            notificationManager.createNotificationChannel(new NotificationChannel(FOREGROUND_NOTIFICATION_CHANNEL_ID_COUNTDOWN_START, "Notification", NotificationManager.IMPORTANCE_HIGH));
            builder.setChannelId(FOREGROUND_NOTIFICATION_CHANNEL_ID_COUNTDOWN_START);
        }
        return builder;
    }
}
