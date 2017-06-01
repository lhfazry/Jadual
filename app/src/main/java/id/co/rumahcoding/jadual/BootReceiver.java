package id.co.rumahcoding.jadual;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import id.co.rumahcoding.jadual.utils.DateUtil;
import id.co.rumahcoding.jadual.utils.PrefsUtil;

/**
 * Created by blastocode on 5/31/17.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        updateAllSchedule(context);
    }

    private void updateAllSchedule(Context context) {
        PrefsUtil prefsUtil = PrefsUtil.getInstance();

        if(prefsUtil.getBooleanState("alarm_subuh", false)) {
            scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("subuh", "")), "subuh");
        }

        if(prefsUtil.getBooleanState("alarm_zuhur", false)) {
            scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("zuhur", "")), "zuhur");
        }

        if(prefsUtil.getBooleanState("alarm_ashar", false)) {
            scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("ashar", "")), "ashar");
        }

        if(prefsUtil.getBooleanState("alarm_maghrib", false)) {
            scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("maghrib", "")), "maghrib");
        }

        if(prefsUtil.getBooleanState("alarm_isya", false)) {
            scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("isya", "")), "isya");
        }
    }

    private void scheduleAdzan(Context context, long alarmTime, String adzan) {
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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (System.currentTimeMillis() > alarmTime) {
            alarmTime = alarmTime + 24 * 60 * 60 * 1000;
        }

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                alarmTime, AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
