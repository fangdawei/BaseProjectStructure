package com.david.baseprojectstructure.ui.avtivity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by david on 2017/3/28.
 */

public abstract class BaseActivityWithTitlebar<T extends ViewDataBinding, V extends ViewDataBinding>
    extends BaseBindingActivity {

  protected T mContent;
  protected V mTitlebar;

  protected abstract T createContentViewDataBinding(Bundle savedInstanceState, ViewGroup container);

  protected abstract V createTitlebarDataBinding(Bundle savedInstanceState, ViewGroup container);

  @Override protected void preInit(Bundle savedInstanceState) {

  }

  @Override protected View createToolBar(Bundle savedInstanceState, ViewGroup container) {
    mTitlebar = createTitlebarDataBinding(savedInstanceState, container);
    if (mTitlebar == null) {
      return null;
    } else {
      return mTitlebar.getRoot();
    }
  }

  @Override protected View createContentView(Bundle savedInstanceState, ViewGroup container) {
    mContent = createContentViewDataBinding(savedInstanceState, container);
    if (mContent == null) {
      return null;
    } else {
      return mContent.getRoot();
    }
  }

  @Override protected void onDestroy() {
    if (mContent != null) {
      mContent.unbind();
      mContent = null;
    }
    if (mTitlebar != null) {
      mTitlebar.unbind();
      mTitlebar = null;
    }
    super.onDestroy();
  }
}
