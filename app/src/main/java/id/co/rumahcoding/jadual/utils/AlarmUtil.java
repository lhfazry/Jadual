package id.co.rumahcoding.jadual.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by fazri on 4/2/2015.
 */
public class AlarmUtil {
    private static final String TAG = "AlarmUtil";
    public static final String ALARM_SHOW_UP = "alarm_show_up";

    public static void cancelAlarm(Context context, Class receiver) {
        Log.d(TAG, "Canceling alarm");
        Intent intent = new Intent(context, receiver);
        intent.setAction(ALARM_SHOW_UP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pendingIntent);
    }

    public static void createAlarm(Context context, long time, Class receiver) {
        Log.d(TAG, "Creating alarm: " + time);
        Intent intent = new Intent(context, receiver);
        intent.setAction(ALARM_SHOW_UP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, time, 1000*60*60*24, pendingIntent);
    }
}
