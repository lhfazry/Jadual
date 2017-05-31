package id.co.rumahcoding.jadual.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

import id.co.rumahcoding.jadual.R;


public class NotificationUtil {
    public static Notification createNotification(Context context, PendingIntent contentIntent, 
            String title, String subject, boolean audio, String audioname, boolean cancelable) {
        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getPackageName() + "/audio/" + audioname);

        Bitmap bitmap = BitmapFactory.decodeResource( context.getResources(), R.mipmap.ic_launcher);
        Builder builder = new Builder(context)
        .setContentTitle(title)
        .setContentText(subject)
        //.setSmallIcon(R.drawable.ic_small_icon)
                .setLargeIcon(bitmap)
        .setContentIntent(contentIntent);

        if(audio) {
            builder.setSound(alarmSound);
        }
                
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        if(!cancelable) {
            notification.flags |= Notification.FLAG_NO_CLEAR;
        }

        return notification;
    }

    public static Notification createBigNotification(Context context, PendingIntent contentIntent,
                                                  String title, String subject, String[] lines,
                                                     boolean audio, boolean cancelable) {
        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getPackageName() + "/audio/notif");

        Bitmap bitmap = BitmapFactory.decodeResource( context.getResources(), R.mipmap.ic_launcher);
        Builder builder = new Builder(context)
                .setContentTitle(title)
                .setContentText(subject)
                .setLargeIcon(bitmap)
                //.setSmallIcon(R.drawable.ic_small_icon)
                .setContentIntent(contentIntent);

        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();

        for (int i=0; i < lines.length; i++) {
            inboxStyle.addLine(lines[i]);
        }

        inboxStyle.setBigContentTitle(subject);
        builder.setStyle(inboxStyle);

        if(audio) {
            builder.setSound(alarmSound);
        }

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        if(!cancelable) {
            notification.flags |= Notification.FLAG_NO_CLEAR;
        }

        return notification;
    }

    public static void hideNotification(Context c, int notifId) {
        try {
            NotificationManager nm = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(notifId);
        }
        catch(Exception e){}
    }
}
