package id.co.rumahcoding.jadual;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import id.co.rumahcoding.jadual.utils.DateUtil;
import id.co.rumahcoding.jadual.utils.PrefsUtil;
import id.co.rumahcoding.jadual.utils.ScheduleUtil;

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
            ScheduleUtil.scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("subuh", "")), "subuh");
        }

        if(prefsUtil.getBooleanState("alarm_zuhur", false)) {
            ScheduleUtil.scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("zuhur", "")), "zuhur");
        }

        if(prefsUtil.getBooleanState("alarm_ashar", false)) {
            ScheduleUtil.scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("ashar", "")), "ashar");
        }

        if(prefsUtil.getBooleanState("alarm_maghrib", false)) {
            ScheduleUtil.scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("maghrib", "")), "maghrib");
        }

        if(prefsUtil.getBooleanState("alarm_isya", false)) {
            ScheduleUtil.scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState("isya", "")), "isya");
        }
    }
}
