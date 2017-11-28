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

  protected T mContent;

  protected abstract T createBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    if (mContent == null) {
      mContent = createBinding(inflater, container, savedInstanceState);
    }
    return mContent != null ? mContent.getRoot() : null;
  }

  @Override public void onDestroy() {
    if (mContent != null) {
      mContent.unbind();
      mContent = null;
    }
    super.onDestroy();
  }
}
