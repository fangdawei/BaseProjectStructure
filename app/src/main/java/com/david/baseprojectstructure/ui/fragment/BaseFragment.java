package com.david.baseprojectstructure.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by david on 2017/3/28.
 */

public abstract class BaseFragment extends Fragment implements IFragment {

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
    initListener();
    initData();
  }
}
