package com.david.baseprojectstructure.ui.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by david on 2017/3/28.
 */

public abstract class BaseBindingFragment<T extends ViewDataBinding> extends BaseFragment {

  protected T mVDB;

  protected abstract T createBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

  @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    mVDB = createBinding(inflater, container, savedInstanceState);
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override public void onDestroy() {
    if(mVDB != null){
      mVDB.unbind();
    }

    super.onDestroy();
  }
}
