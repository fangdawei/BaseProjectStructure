package com.david.baseprojectstructure.ui.dismissable;

/**
 * Created by david on 2017/4/21.
 */

public interface Dismissable {

  boolean isShowing();

  void dismiss();

  void release();
}
