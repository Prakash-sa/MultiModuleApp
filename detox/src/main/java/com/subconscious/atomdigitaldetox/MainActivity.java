package com.subconscious.atomdigitaldetox;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import com.subconscious.atomdigitaldetox.helper.NotificationCountdownUtils;
import com.subconscious.atomdigitaldetox.helper.PermissionUtil;
import com.subconscious.atomdigitaldetox.helper.PermissionsDataConfigurer;
import com.subconscious.atomdigitaldetox.models.PermissionData;
import com.subconscious.atomdigitaldetox.services.ForeGroundServices;
import com.subconscious.atomdigitaldetox.store.PermissionsStore;

import java.util.ArrayList;

import static com.subconscious.atomdigitaldetox.constants.Notifications.NOTIFICATION_ID_COUNTDOWN_START;

public class MainActivity extends AppCompatActivity {


    private ArrayList<PermissionData> permissionDataArrayList;
    private PermissionsStore permissionsStore;
    private Button startbt,stopbt,backto;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startbt=findViewById(R.id.start);
        stopbt=findViewById(R.id.stop);
        backto=findViewById(R.id.back);
        backto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
              //  startActivity(new Intent(Intent.ACTION_VIEW).setClassName("com.example.multimodule","com.example.multimodule."+"MainActivity"));
            }
        });
        getPermission();
        if(checkPermission()){
            countDownNotification();
            startbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startService(new Intent(MainActivity.this, ForeGroundServices.class));
                }
            });
            stopbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopService(new Intent(MainActivity.this, ForeGroundServices.class));
                }
            });
        }
        else {
            givePermission();
        }

    }


    private void countDownNotification(){
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder=NotificationCountdownUtils.displayNotificationForTree(this,notificationManager);
        NotificationCountdownUtils.startCountDown(notificationManager,builder,this,NOTIFICATION_ID_COUNTDOWN_START ,6000);
    }



    private void getPermission(){
        PermissionsDataConfigurer.getInstance(this);
        permissionsStore = PermissionsStore.getInstance();
        permissionDataArrayList = permissionsStore.getPermissionsData();
    }

    private boolean checkPermission(){
        int permissionGrantedCount = 0;
        int totalPermissions = 0;
        for(PermissionData permissionData: permissionDataArrayList)
        {
            permissionData.setPermissionGranted(isPermissionEnabled(permissionData.getId()));
            if (permissionData.isPermissionGranted())
                permissionGrantedCount++;
            totalPermissions++;
        }
        if(permissionGrantedCount!=totalPermissions)return false;
        return true;
    }

    public boolean isPermissionEnabled(int permissionId)
    {
        switch (permissionId)
        {
            case PermissionUtil.USAGE_ACCESS:
                return PermissionUtil.isUsageAccessEnabled(this);
            default:
                break;
        }
        return false;
    }

    private void givePermission(){
        for(PermissionData permissionData: permissionDataArrayList)
        {
            permissionData.setPermissionGranted(isPermissionEnabled(permissionData.getId()));
            if (permissionData.isPermissionGranted())continue;
            else
            {
                if(permissionData.getId()==PermissionUtil.USAGE_ACCESS)PermissionUtil.requestUsageAccess(this);
            }
        }
    }

}
