package com.david.baseprojectstructure.mvp;

/**
 * Created by david on 2017/11/24.
 */

public interface FailtureListener {
  void onFail(int responseCode, String msg, String responseStr);
}
