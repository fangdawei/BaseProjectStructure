package com.david.baseprojectstructure.ui.avtivity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by david on 2017/5/1.
 */

public abstract class BaseActivityNoTitlebar<T extends ViewDataBinding> extends BaseBindingActivity {

  protected T mContent;

  protected abstract T createContentViewDataBinding(Bundle savedInstanceState, ViewGroup container);


  @Override protected void preInit(Bundle savedInstanceState) {

  }

  @Override protected View createToolBar(Bundle savedInstanceState, ViewGroup container) {
    return  null;
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
    super.onDestroy();
  }
}
