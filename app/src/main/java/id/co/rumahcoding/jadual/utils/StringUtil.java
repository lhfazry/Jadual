package id.co.rumahcoding.jadual.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by fazri on 3/6/2015.
 */
public class StringUtil {
    private static final String TAG = "StringUtil";

    public static String stripBase64Header(final String base64) {
        Log.i(TAG, "Stripping: " + base64);

        if(!TextUtils.isEmpty(base64)) {
            final String stripped = base64.replaceFirst("^data:image/[^;]+;base64,", "");
            Log.i(TAG, "Stripped: " + stripped);
            return stripped;
        }

        return null;
    }

    public static String joins(final Object[] strings, final String separator) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);

            if (i != strings.length - 1) {
                sb.append(separator);
            }
        }

        return  sb.toString();
    }
}
