package com.david.baseprojectstructure.ui.avtivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import com.david.baseprojectstructure.databinding.ActivityHomeBinding;
import com.david.baseprojectstructure.databinding.ToolbarHomeBinding;
import com.david.baseprojectstructure.di.component.DaggerHomeComponent;
import com.david.baseprojectstructure.di.module.HomeModule;
import com.david.baseprojectstructure.mvp.contract.HomeContract;
import javax.inject.Inject;

public class HomeActivity extends BaseActivityWithTitlebar<ActivityHomeBinding, ToolbarHomeBinding>
    implements HomeContract.View {

  @Inject HomeContract.Presenter presenter;

  public static void startActivity(Context context) {
    Intent intent = new Intent(context, HomeActivity.class);
    context.startActivity(intent);
  }

  @Override protected ActivityHomeBinding createContentViewDataBinding(Bundle savedInstanceState, ViewGroup container) {
    return ActivityHomeBinding.inflate(getLayoutInflater(), container, false);
  }

  @Override protected ToolbarHomeBinding createTitlebarDataBinding(Bundle savedInstanceState, ViewGroup container) {
    return ToolbarHomeBinding.inflate(getLayoutInflater(), container, false);
  }

  @Override protected void preInit(Bundle savedInstanceState) {
    super.preInit(savedInstanceState);
    DaggerHomeComponent.builder().homeModule(new HomeModule(this)).build().inject(this);
  }

  @Override public void initView() {

  }

  @Override public void initListener() {

  }

  @Override public void initData() {

  }

  @Override public void release() {

  }
}
