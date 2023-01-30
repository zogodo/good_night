package me.zogodo.androiddemo;

import android.app.usage.EventStats;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyUsage
{
    public static void TestEvent(Context context)
    {
        long endTime = System.currentTimeMillis();
        long beginTime = endTime - 1000 * 60 * 60 * 3; //最近3小时
        UsageStatsManager manager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents usageEvents = manager.queryEvents(beginTime, endTime);

        UsageEvents.Event eventOut;
        Log.e("zzze0", "a", null);
        while (usageEvents.hasNextEvent()) {
            eventOut = new UsageEvents.Event();
            usageEvents.getNextEvent(eventOut);
            Timestamp ts = new Timestamp(eventOut.getTimeStamp());
            int t = eventOut.getEventType();
            if (t == UsageEvents.Event.SCREEN_INTERACTIVE || t == UsageEvents.Event.SCREEN_NON_INTERACTIVE)
            {
                Log.e("zzze1", ts.toString() + " t = " + eventOut.getEventType(), null);
            }
        }
        Log.e("zzze0", "z", null);
    }
}
