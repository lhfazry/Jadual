package id.co.rumahcoding.jadual.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fazri on 4/1/2015.
 */
public class DateUtil {
    private static final String TAG = "DateUtil";
    private static final String[] hijriNames = new String[]{
            "Muharram", "Safar", "Rabiul Awal", "Rabiul Akhir", "Jumadil Awal", "Jumadil Akhir", "Rajab", "Sya'ban", "Ramadhan", "Syawal", "Dzulqaidah", "Dzulhijjah"
    };

    public static Date parse(final String dateString) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return dateFormat.parse(dateString);
    }

    public static String pretty(final Date date) {
        DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMM yyyy", new Locale("in_ID"));
        return dateFormat.format(date);
    }

    public static String format(final Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static String formatMySQL(final Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static String formatTime(final Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static String onlyTime(final Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        return dateFormat.format(date);
    }

    public static long hourToMillis(final String hour) {
        String[] hours = hour.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(hours[1]));
        return calendar.getTimeInMillis();
    }

    public static String hijri(int date, int month, int year) {
        return "" + date + " " + hijriNames[month - 1] + " " + year + "H";
    }

}
