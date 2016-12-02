package com.slut.recorder.utils;

import com.slut.recorder.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 七月在线科技 on 2016/12/1.
 */

public class TimeUtils {

    private static final String DATE_AND_MINUTE = "MM-dd HH:mm";
    private static final String YEAR_MONTH_DAY = "yyyy-MM-dd";

    public static String interval2Str(long startTime, long endTime) {
        if (endTime < startTime) {
            return ResUtils.getString(R.string.error_time_end_over_start);
        }
        long oneSecond = 1000;
        long oneMinute = 60 * oneSecond;
        long oneHour = 60 * oneMinute;
        long oneDay = 24 * oneHour;
        long oneYear = 365 * oneDay;
        long interval = endTime - startTime;
        if (interval < oneMinute) {
            return ResUtils.getString(R.string.time_just_now);
        } else if (interval < oneHour) {
            return interval / oneMinute + ResUtils.getString(R.string.time_minute);
        } else if (interval < oneDay) {
            return interval / oneHour + ResUtils.getString(R.string.time_hour);
        } else if (interval < oneYear) {
            return stamp2Date(startTime,DATE_AND_MINUTE);
        } else {
            return stamp2Date(startTime,YEAR_MONTH_DAY);
        }
    }

    public static String stamp2Date(long stamp,String fmt) {
        SimpleDateFormat format = new SimpleDateFormat(fmt);
        Long time = new Long(stamp);
        String d = format.format(time);
        try {
            Date date = format.parse(d);
            return date.toString();
        } catch (Exception e) {
            return "error while transfer time";
        }
    }

}
