package id.co.rumahcoding.jadual.utils;

import java.util.Calendar;

public class TimeUtil {
    public static long getClosestTimeMillis(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);        
        
        long current = System.currentTimeMillis();
        
        if(cal.getTimeInMillis() < current) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        return cal.getTimeInMillis();
    }
}
