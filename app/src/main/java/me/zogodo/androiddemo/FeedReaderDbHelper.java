package me.zogodo.androiddemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "screen_usage.db";

    private static final String SQL_CREATE_EVENT =
            "CREATE TABLE event(`_id` INTEGER PRIMARY KEY, `time` datetime, `type` INTEGER);";

    private static final String SQL_CREATE_INFO =
            "CREATE TABLE info(`key` TEXT PRIMARY KEY, `value` TEXT);";

    private static final String SQL_TEST_INSERT =
            "insert into `event`(`time`, `type`) values(1, 2), (3, 4)";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        Log.e("zzz", "db onCreate()");
        db.execSQL(SQL_CREATE_EVENT);
        db.execSQL(SQL_CREATE_INFO);
        db.execSQL(SQL_TEST_INSERT);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("zzz", "db onUpgrade()");
        //升级时改动数据库
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("zzz", "db onDowngrade()");
        this.onUpgrade(db, oldVersion, newVersion);
    }

    public static Map<Long, Integer> GetAllEvent() {
        SQLiteDatabase db = MainActivity.dbHelper.getWritableDatabase();
        String sql = "select * from event";
        Cursor cursor = db.rawQuery(sql, null);

        Map<Long, Integer> events = new HashMap<>();
        try {
            while (cursor.moveToNext()) {
                Map<Date, Integer> event = new HashMap<>();
                events.put(cursor.getLong(cursor.getColumnIndexOrThrow("time")), cursor.getInt(cursor.getColumnIndexOrThrow("type")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();

        return events;
    }

}
