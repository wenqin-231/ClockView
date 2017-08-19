package com.lewis.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author wenqin 2017-07-21 10:42
 */

public class TimeUtils {

    public static String Mills2String(long timeMills, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(new Date(timeMills));
    }

    public static int Mills2Hour(long timeMills) {
        return Integer.valueOf(Mills2String(timeMills, "HH"));
    }

    public static int Mills2Minute(long timeMills) {
        return Integer.valueOf(Mills2String(timeMills, "mm"));
    }
}
