package com.subconscious.atomdigitaldetox.helper;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class RunningPackageUtil {

    public static String getTopPackageName(Context context){
        String topPackageName="null";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            // We get usage stats for the last 10 seconds
            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);
            // Sort the stats by the last time used
            if (stats != null) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : stats) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (!mySortedMap.isEmpty()) {
                    topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        }
        return topPackageName;
    }

    public static String getLauncherPackage(Context context){
        Intent intent= new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo defaultLauncher =context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return defaultLauncher.activityInfo.packageName;
    }

    public static boolean isAnotherApplicationRunning(String topPackageName,String defaultLauncherStr,String applicationPackageName){
        if (!topPackageName.equals(defaultLauncherStr) && !topPackageName.equals(applicationPackageName) && !topPackageName.equals("null")) {
            return true;
        }
        return false;
    }
}
