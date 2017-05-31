package id.co.rumahcoding.jadual;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import id.co.rumahcoding.jadual.utils.NotificationUtil;

/**
 * Created by blastocode on 5/30/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "alarm received");
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, i,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = NotificationUtil.createNotification(context,
                pendingIntent, "Jadual",
                "Waktu azan", true, "adzan", true);

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
