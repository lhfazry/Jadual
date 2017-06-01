package id.co.rumahcoding.jadual;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import id.co.rumahcoding.jadual.utils.DateUtil;
import id.co.rumahcoding.jadual.utils.NotificationUtil;
import id.co.rumahcoding.jadual.utils.PrefsUtil;
import id.co.rumahcoding.jadual.utils.ScheduleUtil;

/**
 * Created by blastocode on 5/30/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "alarm received");

        String adzan = intent.getStringExtra("adzan");
        PrefsUtil prefsUtil = PrefsUtil.getInstance();
        ScheduleUtil.scheduleAdzan(context, DateUtil.hourToMillis(prefsUtil.getStringState(adzan, "")), adzan);
        NotificationUtil.showAdzanNotification(context, adzan);
    }
}
