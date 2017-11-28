package com.david.baseprojectstructure.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.david.baseprojectstructure.common.Logger;
import com.david.baseprojectstructure.common.Toaster;
import com.david.baseprojectstructure.mvp.DisposableWatcher;
import com.david.baseprojectstructure.mvp.view.IView;
import com.david.baseprojectstructure.net.NoNetworkException;
import com.david.baseprojectstructure.ui.avtivity.BaseActivity;
import com.david.baseprojectstructure.ui.dismissable.Dismissable;
import com.david.baseprojectstructure.ui.dismissable.DismissableWatcher;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2017/3/28.
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment
    implements IFragment, DismissableWatcher, IView {

  private List<Dismissable> dismissableList = new ArrayList<>();
  private List<DisposableWatcher> disposableWatcherList = new ArrayList<>();

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    preInit(view, savedInstanceState);

    Logger.d(getClass().getSimpleName(), "fragment init start");
    initView();
    initListener();
    initData();
    Logger.d(getClass().getSimpleName(), "fragment init end");
  }

  @Override public void onDestroy() {
    release();
    releaseDismissables();
    super.onDestroy();
  }

  protected void preInit(View view, Bundle savedInstanceState) {

  }

  protected void hideBottomUiMenu() {
    Activity activity = getActivity();
    if (activity instanceof BaseActivity) {
      final BaseActivity host = (BaseActivity) activity;
      getView().postDelayed(() -> host.hideBottomUiMenu(), 500);
    }
  }

  public BaseActivity getBaseActivity() {
    Activity activity = getActivity();
    if (activity instanceof BaseActivity) {
      return (BaseActivity) activity;
    } else {
      return null;
    }
  }

  public void requstPermisson(String permission, BaseActivity.PermissionListener listener) {
    BaseActivity baseBaseActivity = getBaseActivity();
    if (baseBaseActivity == null) {
      return;
    }
    baseBaseActivity.requestPermission(permission, listener);
  }

  protected void registerDisposeWatcher(DisposableWatcher watcher) {
    disposableWatcherList.add(watcher);
  }

  protected void unregisterDisposeWatcher(DisposableWatcher watcher) {
    disposableWatcherList.remove(watcher);
  }

  @Override public void registerDismissable(Dismissable dismissable) {
    dismissableList.add(dismissable);
  }

  @Override public void unregisterDismissable(Dismissable dismissable) {
    dismissableList.remove(dismissable);
  }

  @Override public void releaseDismissables() {
    for (Dismissable dismissable : dismissableList) {
      dismissable.release();
    }
    dismissableList.clear();
  }

  @Override public void onNetworkError(Throwable throwable) {
    if (throwable instanceof NoNetworkException) {
      Toaster.toast("无网络，请检查网络连接");
    } else {
      Toaster.toast(throwable.getMessage());
    }
  }
}
