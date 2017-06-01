package id.co.rumahcoding.jadual;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.chrono.IslamicChronology;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.rumahcoding.jadual.utils.DateUtil;
import id.co.rumahcoding.jadual.utils.NotificationUtil;
import id.co.rumahcoding.jadual.utils.PopupUtil;
import id.co.rumahcoding.jadual.utils.PrefsUtil;
import id.co.rumahcoding.jadual.utils.ScheduleUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.current_time)
    TextView currentTimeTextView;

    @BindView(R.id.date_masehi)
    TextView masehiDateTextView;

    @BindView(R.id.date_hijri)
    TextView hijriDateTextView;

    @BindView(R.id.time_subuh)
    TextView subuhTimeTextView;

    @BindView(R.id.time_zuhur)
    TextView zuhurTimeTextView;

    @BindView(R.id.time_ashar)
    TextView asharTimeTextView;

    @BindView(R.id.time_maghrib)
    TextView maghribTimeTextView;

    @BindView(R.id.time_isya)
    TextView isyaTimeTextView;

    @BindView(R.id.alarm_subuh)
    ImageButton alarmSubuhImageButton;

    @BindView(R.id.alarm_zuhur)
    ImageButton alarmZuhurImageButton;

    @BindView(R.id.alarm_ashar)
    ImageButton alarmAsharImageButton;

    @BindView(R.id.alarm_maghrib)
    ImageButton alarmMaghribmageButton;

    @BindView(R.id.alarm_isya)
    ImageButton alarmIsyaImageButton;

    private OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Date date = new Date();
        String masehi = DateUtil.pretty(date);
        masehiDateTextView.setText(masehi);

        DateTime dateTime = new DateTime();
        DateTime islamicDateTime = dateTime.withChronology(IslamicChronology.getInstance());

        int hDate = islamicDateTime.getDayOfMonth();
        int hMonth = islamicDateTime.getMonthOfYear();
        int hYear = islamicDateTime.getYear();

        String hijri = DateUtil.hijri(hDate, hMonth, hYear);
        hijriDateTextView.setText(hijri);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updateTime();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);

        String today = DateUtil.formatMySQL(date);
        PrefsUtil prefsUtil = PrefsUtil.getInstance();
        String lastUpdated = prefsUtil.getStringState("last_updated", "");

        if(!today.equals(lastUpdated)) {
            getJadwal();
        }

        updateLayout();

        long time = DateUtil.hourToMillis("08:40");
        ScheduleUtil.scheduleAdzan(this, time, "subuh");
    }

    private void updateLayout() {
        PrefsUtil prefsUtil = PrefsUtil.getInstance();
        String subuh = prefsUtil.getStringState("subuh", "");
        String zuhur = prefsUtil.getStringState("zuhur", "");
        String ashar = prefsUtil.getStringState("ashar", "");
        String maghrib = prefsUtil.getStringState("maghrib", "");
        String isya = prefsUtil.getStringState("isya", "");

        subuhTimeTextView.setText(subuh);
        zuhurTimeTextView.setText(zuhur);
        asharTimeTextView.setText(ashar);
        maghribTimeTextView.setText(maghrib);
        isyaTimeTextView.setText(isya);

        updateAlarmButton(alarmSubuhImageButton, prefsUtil.getBooleanState("alarm_subuh", false));
        updateAlarmButton(alarmZuhurImageButton, prefsUtil.getBooleanState("alarm_zuhur", false));
        updateAlarmButton(alarmAsharImageButton, prefsUtil.getBooleanState("alarm_ashar", false));
        updateAlarmButton(alarmMaghribmageButton, prefsUtil.getBooleanState("alarm_maghrib", false));
        updateAlarmButton(alarmIsyaImageButton, prefsUtil.getBooleanState("alarm_isya", false));
    }

    private void updateTime() {
        Date date = new Date();
        final String time = DateUtil.onlyTime(date);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentTimeTextView.setText(time);
            }
        });
    }

    private void getJadwal()  {
        PopupUtil.showLoading(this, "", "Loading ...");

        Request request = new Request.Builder()
                .url("http://api.aladhan.com/timings?latitude=-6.3648506&longitude=106.8473377&timezonestring=Asia/Jakarta")
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                PopupUtil.dismissDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                PopupUtil.dismissDialog();
                String body = response.body().string();

                try {
                    JSONObject json = new JSONObject(body);
                    int code = json.getInt("code");

                    if(code == 200) {
                        JSONObject data = json.getJSONObject("data");
                        JSONObject timings = data.getJSONObject("timings");

                        final String subuh = timings.getString("Fajr");
                        final String zuhur = timings.getString("Dhuhr");
                        final String ashar = timings.getString("Asr");
                        final String maghrib = timings.getString("Maghrib");
                        final String isya = timings.getString("Isha");

                        Date date = new Date();
                        String sdate = DateUtil.formatMySQL(date);

                        PrefsUtil prefsUtil = PrefsUtil.getInstance();
                        prefsUtil.setStringState("last_updated", sdate);
                        prefsUtil.setStringState("subuh", subuh);
                        prefsUtil.setStringState("zuhur", zuhur);
                        prefsUtil.setStringState("ashar", ashar);
                        prefsUtil.setStringState("maghrib", maghrib);
                        prefsUtil.setStringState("isya", isya);

                        ScheduleUtil.cancelAllAlarm(MainActivity.this);
                        updateAllSchedule();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                subuhTimeTextView.setText(subuh);
                                zuhurTimeTextView.setText(zuhur);
                                asharTimeTextView.setText(ashar);
                                maghribTimeTextView.setText(maghrib);
                                isyaTimeTextView.setText(isya);
                            }
                        });
                    }
                }
                catch (JSONException e) {

                }
            }
        });
    }

    private void updateAllSchedule() {
        PrefsUtil prefsUtil = PrefsUtil.getInstance();

        if(prefsUtil.getBooleanState("alarm_subuh", false)) {
            ScheduleUtil.scheduleAdzan(this, DateUtil.hourToMillis(prefsUtil.getStringState("subuh", "")), "subuh");
        }

        if(prefsUtil.getBooleanState("alarm_zuhur", false)) {
            ScheduleUtil.scheduleAdzan(this, DateUtil.hourToMillis(prefsUtil.getStringState("zuhur", "")), "zuhur");
        }

        if(prefsUtil.getBooleanState("alarm_ashar", false)) {
            ScheduleUtil.scheduleAdzan(this, DateUtil.hourToMillis(prefsUtil.getStringState("ashar", "")), "ashar");
        }

        if(prefsUtil.getBooleanState("alarm_maghrib", false)) {
            ScheduleUtil.scheduleAdzan(this, DateUtil.hourToMillis(prefsUtil.getStringState("maghrib", "")), "maghrib");
        }

        if(prefsUtil.getBooleanState("alarm_isya", false)) {
            ScheduleUtil.scheduleAdzan(this, DateUtil.hourToMillis(prefsUtil.getStringState("isya", "")), "isya");
        }
    }

    @OnClick({R.id.alarm_subuh, R.id.alarm_zuhur, R.id.alarm_ashar,
            R.id.alarm_maghrib, R.id.alarm_isya })
    public void onAlarmClicked(ImageButton button) {
        PrefsUtil prefsUtil = PrefsUtil.getInstance();
        Drawable drawable = button.getDrawable();
        Drawable volumeUp = getResources().getDrawable(R.drawable.ic_volume_up);
        Drawable volumeOff = getResources().getDrawable(R.drawable.ic_volume_off);

        String alarm = "";
        int alarmId = button.getId();

        switch (alarmId) {
            case R.id.alarm_subuh:
                alarm = "alarm_subuh";
                break;
            case R.id.alarm_zuhur:
                alarm = "alarm_zuhur";
                break;
            case R.id.alarm_ashar:
                alarm = "alarm_ashar";
                break;
            case R.id.alarm_maghrib:
                alarm = "alarm_maghrib";
                break;
            case R.id.alarm_isya:
                alarm = "alarm_isya";
                break;
        }

        if(drawable.getConstantState().equals(volumeUp.getConstantState())) {
            button.setImageDrawable(volumeOff);
            prefsUtil.setBooleanState(alarm, false);
        }
        else {
            button.setImageDrawable(volumeUp);
            prefsUtil.setBooleanState(alarm, true);
        }

        ScheduleUtil.cancelAllAlarm(this);
        updateAllSchedule();
    }

    private void updateAlarmButton(ImageButton button, boolean isAlarmOn) {
        Log.d(TAG, "Update alarm button to " + isAlarmOn);

        if(isAlarmOn) {
            button.setImageResource(R.drawable.ic_volume_up);
        }
        else {
            button.setImageResource(R.drawable.ic_volume_off);
        }
    }

    @OnClick(R.id.current_time)
    public void onTimeClicked() {
        NotificationUtil.showAdzanNotification(this, "adzan");
    }


}
