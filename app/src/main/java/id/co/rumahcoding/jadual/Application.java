package id.co.rumahcoding.jadual;

import android.util.Log;

import id.co.rumahcoding.jadual.utils.PrefsUtil;

/**
 * Created by blastocode on 5/30/17.
 */

public class Application extends android.app.Application {
    private static final String TAG = "Application";

    @Override
    public void onCreate() {
        super.onCreate();

        PrefsUtil.initInstance(this);
        PrefsUtil prefsUtil = PrefsUtil.getInstance();
        boolean firstLaunch = prefsUtil.getBooleanState("first_launch", true);

        Log.d(TAG, "is first launch: " + firstLaunch);

        if(firstLaunch) {
            prefsUtil.setBooleanState("first_launch", false);
            prefsUtil.setBooleanState("alarm_subuh", true);
            prefsUtil.setBooleanState("alarm_zuhur", false);
            prefsUtil.setBooleanState("alarm_ashar", false);
            prefsUtil.setBooleanState("alarm_maghrib", true);
            prefsUtil.setBooleanState("alarm_isya", true);
        }
    }
}
