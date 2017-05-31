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
            scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("subuh", "")));
        }

        if(prefsUtil.getBooleanState("alarm_zuhur", false)) {
            scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("zuhur", "")));
        }

        if(prefsUtil.getBooleanState("alarm_ashar", false)) {
            scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("ashar", "")));
        }

        if(prefsUtil.getBooleanState("alarm_maghrib", false)) {
            scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("maghrib", "")));
        }

        if(prefsUtil.getBooleanState("alarm_isya", false)) {
            scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("isya", "")));
        }
    }

    private void scheduleAdzan(Context context, long alarmTime) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (System.currentTimeMillis() > alarmTime) {
            alarmTime = alarmTime + 24 * 60 * 60 * 1000;
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                alarmTime, AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}
