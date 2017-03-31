package com.david.baseprojectstructure.util;

import android.util.Log;

/**
 * Created by david on 2017/3/21.
 */

public class LogUtils {

  public static void d(String tag, String msg){
    Log.d(tag, msg);
  }

  public static void e(String tag, String msg){
    Log.e(tag, msg);
  }

  public static void i(String tag, String msg){
    Log.i(tag, msg);
  }
}
