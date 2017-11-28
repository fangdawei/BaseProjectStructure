package com.david.baseprojectstructure.ui.fragment;

/**
 * Created by david on 2017/3/28.
 */

public interface IFragment {
  void initView();

  void initListener();

  void initData();

  void release();
}
