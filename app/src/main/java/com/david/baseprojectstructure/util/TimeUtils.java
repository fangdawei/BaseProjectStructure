package com.david.baseprojectstructure.util;

import android.text.TextUtils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by david on 2017/3/21.
 */

public class TimeUtils {

  public static final String TAG = "TimeUtil";

  public static final String HH_MM = "HH:mm";
  public static final String MM_SS = "mm:ss";
  public static final String HH_MM_SS = "HH:mm:ss";
  public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
  public static final String YYYY_MM_DD = "yyyy-MM-dd";
  public static final String MM_DD = "MM-dd";
  public static final String DD_MM_YYYY = "dd/MM/yyyy";
  public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";

  /**
   * 获取当前时间，单位为毫秒
   */
  public static long getCurrentTimeMsec() {
    return System.currentTimeMillis();
  }

  /**
   * 获取当前时间，单位为秒
   */
  public static long getCurrentTimeSec() {
    return getCurrentTimeMsec() / 1000;
  }

  /**
   * 获取当前字符串时间
   */
  public static String getCurrentTimeStr(String format) {
    return formatTime(getCurrentTimeSec(), format);
  }

  public static String formatTime(long millis, String pattern) {
    Date date = new Date(millis);
    if (TextUtils.isEmpty(pattern)) {
      pattern = DD_MM_YYYY_HH_MM_SS;
    }
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    return simpleDateFormat.format(date);
  }

  public static String getTimeIntervalInfo(long millis) {
    long interval = getCurrentTimeMsec() - millis;
    interval = interval / 1000;
    if (interval < 60) {
      return "刚刚";
    }
    interval = interval / 60;
    if (interval < 60){
      return interval + "分钟前";
    }
    interval = interval / 60;
    if (interval < 24){
      return interval + "小时前";
    }
    TimeBean timeBean = new TimeBean(millis);
    return String.format("%d年%d月%d日", timeBean.year, timeBean.month, timeBean.day);
  }

  static class TimeBean {
    public long timeMsec;
    public int year;//年
    public int month;//月
    public int day;//日
    public int hour;//时
    public int minute;//分
    public int second;//秒
    public int msec;//毫秒

    public TimeBean(long millis) {
      this.timeMsec = millis;
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(millis);
      this.year = calendar.get(Calendar.YEAR) + 1900;
      this.month = calendar.get(Calendar.MONTH) + 1;
      this.day = calendar.get(Calendar.DAY_OF_MONTH);
      this.hour = calendar.get(Calendar.HOUR_OF_DAY);
      this.minute = calendar.get(Calendar.MINUTE);
      this.second = calendar.get(Calendar.SECOND);
      this.msec = calendar.get(Calendar.MILLISECOND);
    }
  }
}
