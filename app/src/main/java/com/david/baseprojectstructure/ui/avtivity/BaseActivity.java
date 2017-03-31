package com.david.baseprojectstructure.ui.avtivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.david.baseprojectstructure.R;
import com.david.baseprojectstructure.databinding.ActivityBaseBinding;

/**
 * Created by david on 2017/3/16.
 */

public abstract class BaseActivity
    extends AppCompatActivity implements IActivity {

  private ActivityBaseBinding baseVDB;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /** 开始加载布局 **/
    baseVDB = DataBindingUtil.setContentView(this, R.layout.activity_base);
    View toolBarView = createToolBar(savedInstanceState);
    if (toolBarView != null) {
      baseVDB.contentRoot.addView(toolBarView,
          ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    View contentView = createContentView(savedInstanceState);
    if (contentView != null) {
      baseVDB.contentRoot.addView(contentView,
          ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    /** 加载布局结束 **/
    preInit(savedInstanceState);
    initView();
    initListener();
    initData();
  }

  protected void preInit(Bundle savedInstanceState) {

  }

  protected abstract View createToolBar(Bundle savedInstanceState);

  protected abstract View createContentView(Bundle savedInstanceState);
}
