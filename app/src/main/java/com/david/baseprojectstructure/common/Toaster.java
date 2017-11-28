package com.david.baseprojectstructure.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by david on 2017/4/10.
 */

public class Toaster {

  private static Context context;

  public static void init(Context context) {
    Toaster.context = context;
  }

  public static void toast(String msg) {
    check();
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
  }

  private static void check(){
    if(context == null){
      throw new RuntimeException("未执行初始化，请先调用init方法");
    }
  }
}
