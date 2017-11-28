package com.david.baseprojectstructure.ui.avtivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import com.david.baseprojectstructure.R;
import com.david.baseprojectstructure.databinding.ActivityBaseBinding;

/**
 * Created by david on 2017/11/16.
 */

public abstract class BaseBindingActivity extends BaseActivity {

  protected ActivityBaseBinding mRoot;

  @Override protected View createView(@Nullable Bundle savedInstanceState) {
    mRoot = DataBindingUtil.setContentView(this, R.layout.activity_base);
    View toolBarView = createToolBar(savedInstanceState, mRoot.toolbarContainer);
    if (toolBarView != null) {
      mRoot.toolbarContainer.addView(toolBarView);
    }
    if (isStatusBarSuspension()) {
      mRoot.toolbarContainer.setPadding(0, 0, 0, 0);
    } else {
      int statusBarHeight = getStatusBarHeight();
      mRoot.toolbarContainer.setPadding(0, statusBarHeight, 0, 0);
      int statusBarColor = getStatusBarColor();
      mRoot.toolbarContainer.setBackgroundColor(statusBarColor);
    }
    View contentView = createContentView(savedInstanceState, mRoot.contentContainer);
    if (contentView != null) {
      mRoot.contentContainer.addView(contentView);
    }
    return mRoot.getRoot();
  }

  /**
   * 创建ToolBar的View
   */
  protected abstract View createToolBar(Bundle savedInstanceState, ViewGroup container);

  /**
   * 创建页面主要内容View
   */
  protected abstract View createContentView(Bundle savedInstanceState, ViewGroup container);

  @Override protected void onDestroy() {
    if (mRoot != null) {
      mRoot.unbind();
      mRoot = null;
    }

    super.onDestroy();
  }
}
