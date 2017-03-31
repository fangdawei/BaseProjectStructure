package com.david.baseprojectstructure.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;

/**
 * Created by david on 2017/3/20.
 */

public class ScreenUtils {

  /**
   * 获取屏幕宽度
   */
  public static int screenWidth(Context context) {
    if (context == null) return 0;
    return context.getResources().getDisplayMetrics().widthPixels;
  }

  /**
   * 获取屏幕高度
   */
  public static int screenHeight(Context context) {
    if (context == null) return 0;
    return context.getResources().getDisplayMetrics().heightPixels;
  }

  /**
   * 获取 ActionBar 高度
   */
  public static int actionBarHeight(Context context) {
    if (context == null) return 0;
    int actionBarHeight = 0;
    TypedValue tv = new TypedValue();
    if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
      actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
    }
    return actionBarHeight;
  }

  /**
   * 获取状态栏高度
   */
  public static int statusBarHeight(Activity activity) {
    if (activity == null) return 0;
    Rect frame = new Rect();
    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
    return frame.top;
  }

  /**
   * sp转px
   */
  public static int sp2px(Context context, float sp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
  }

  /**
   * dp转px
   */
  public static int dp2px(Context context, float dp) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
  }
}
