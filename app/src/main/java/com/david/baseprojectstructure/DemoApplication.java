package com.david.baseprojectstructure;

import android.app.Application;
import com.david.baseprojectstructure.common.FileManager;
import com.david.baseprojectstructure.common.ValueManager;

/**
 * Created by david on 2017/3/16.
 */

public class DemoApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();

    ValueManager.init(this);
    FileManager.init(this);
  }
}
