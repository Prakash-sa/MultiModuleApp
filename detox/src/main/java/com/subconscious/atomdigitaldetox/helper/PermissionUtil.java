package com.subconscious.atomdigitaldetox.helper;


import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import static android.app.AppOpsManager.MODE_ALLOWED;
import static android.app.AppOpsManager.OPSTR_GET_USAGE_STATS;

public class PermissionUtil {
    public static final int USAGE_ACCESS = 1;

    public static boolean isUsageAccessEnabled(Context context){
        AppOpsManager appopmanager=null;
        int mode=0;
        appopmanager= (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        mode =appopmanager.checkOpNoThrow(OPSTR_GET_USAGE_STATS, android.os.Process.myUid(),
                context.getPackageName()
        );
        return mode==MODE_ALLOWED;
    }

    public static void requestUsageAccess(Context context){
        context.startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
    }

}
