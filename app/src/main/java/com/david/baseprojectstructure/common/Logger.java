package com.david.baseprojectstructure.common;

import android.util.Log;

/**
 * Created by david on 2017/10/24.
 */

public class Logger {

  private static boolean isLogOutput = true;
  private static final String TAG = "Paperless -> ";

  public static void open() {
    isLogOutput = true;
  }

  public static void close() {
    isLogOutput = false;
  }

  private static void check() {
    if (!isLogOutput) {
      return;
    }
  }

  public static void i(String msg) {
    check();
    Log.i(TAG, msg);
  }

  public static void i(String tag, String msg) {
    check();
    Log.i(TAG + tag, msg);
  }

  public static void d(String msg) {
    check();
    Log.d(TAG, msg);
  }

  public static void d(String tag, String msg) {
    check();
    Log.d(TAG + tag, msg);
  }

  public static void e(String msg) {
    check();
    Log.e(TAG, msg);
  }

  public static void e(String tag, String msg) {
    check();
    Log.e(TAG + tag, msg);
  }
}
