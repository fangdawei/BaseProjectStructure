package com.david.baseprojectstructure.ui.avtivity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by david on 2017/3/28.
 */

public abstract class BaseBindingActivity<T extends ViewDataBinding> extends BaseActivity {

  protected T mVDB;

  protected abstract T createViewDataBinding(Bundle savedInstanceState);

  @Override protected void preInit(Bundle savedInstanceState) {

  }

  @Override protected View createToolBar(Bundle savedInstanceState) {
    return null;
  }

  @Override protected View createContentView(Bundle savedInstanceState) {
    mVDB = createViewDataBinding(savedInstanceState);
    if(mVDB == null){
      return  null;
    } else {
      return mVDB.getRoot();
    }
  }

  @Override protected void onDestroy() {
    if(mVDB != null){
      mVDB.unbind();
    }

    super.onDestroy();
  }
}
