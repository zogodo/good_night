package me.zogodo.androiddemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;

import java.sql.Timestamp;

public class MyUsage
{
    public static void TestEvent(Context context)
    {
        long endTime = System.currentTimeMillis();
        long beginTime = endTime - 1000 * 60 * 60 * 24; //最近一天
        UsageStatsManager manager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents usageEvents = manager.queryEvents(beginTime, endTime);

        SQLiteDatabase db = MainActivity.dbHelper.getWritableDatabase();

        UsageEvents.Event eventOut;
        Log.e("zzze0", "a", null);
        while (usageEvents.hasNextEvent()) {
            eventOut = new UsageEvents.Event();
            usageEvents.getNextEvent(eventOut);
            Timestamp ts = new Timestamp(eventOut.getTimeStamp());
            int t = eventOut.getEventType();
            if (t == UsageEvents.Event.SCREEN_INTERACTIVE         //亮屏
                || t == UsageEvents.Event.SCREEN_NON_INTERACTIVE  //灭屏
                || t == UsageEvents.Event.KEYGUARD_SHOWN          //锁屏
                || t == UsageEvents.Event.KEYGUARD_HIDDEN)        //解锁
            {
                Log.e("zzze1", ts.toString() + " t = " + eventOut.getEventType(), null);
                String sql = "insert into event(`time`, `type`) values(?, ?)";
                Object[] pras = {eventOut.getTimeStamp(), t};
                db.execSQL(sql, pras);
            }
        }
        Log.e("zzze0", "z", null);
    }
}
