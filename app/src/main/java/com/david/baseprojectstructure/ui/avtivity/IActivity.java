package com.david.baseprojectstructure.ui.avtivity;

/**
 * Created by david on 2017/3/16.
 */

public interface IActivity {
  void initView();
  void initListener();
  void initData();
  void release();
}
