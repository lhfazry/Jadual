package id.co.rumahcoding.jadual.utils;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import id.co.rumahcoding.jadual.AlarmReceiver;

/**
 * Created by blastocode on 6/1/17.
 */

public class ScheduleUtil {
    @TargetApi(19)
    public static void scheduleAdzan(Context context, long alarmTime, String adzan) {
        int id = 0;

        if(adzan.equals("subuh")) {
            id = 0;
        }
        else if(adzan.equals("zuhur")) {
            id = 1;
        }
        else if(adzan.equals("ashar")) {
            id = 2;
        }
        else if(adzan.equals("maghrib")) {
            id = 3;
        }
        else if(adzan.equals("isya")) {
            id = 4;
        }

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("adzan", adzan);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (System.currentTimeMillis() > alarmTime) {
            alarmTime = alarmTime + 24 * 60 * 60 * 1000;
        }

        if(Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                    alarmTime, pendingIntent);
        }
        else {
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    alarmTime, pendingIntent);
        }
    }

    public static void cancelAllAlarm(Context context) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        try {
            alarmManager.cancel(pendingIntent);
        }
        catch (Exception e) {

        }
    }
}
