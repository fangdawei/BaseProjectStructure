package com.david.baseprojectstructure.manager;

import android.content.Context;

/**
 * Created by david on 2017/3/29.
 */

public class ValueManager {

  private static Context context;

  public static void init(Context context) {
    ValueManager.context = context;
  }

  public static String string(int id) {
    return context.getResources().getString(id);
  }
}
