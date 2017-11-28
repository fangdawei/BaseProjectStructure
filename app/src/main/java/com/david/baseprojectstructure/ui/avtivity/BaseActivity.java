package com.david.baseprojectstructure.ui.avtivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import com.david.baseprojectstructure.R;
import com.david.baseprojectstructure.common.Logger;
import com.david.baseprojectstructure.common.Toaster;
import com.david.baseprojectstructure.mvp.DisposableWatcher;
import com.david.baseprojectstructure.mvp.view.IView;
import com.david.baseprojectstructure.net.NoNetworkException;
import com.david.baseprojectstructure.ui.dismissable.Dismissable;
import com.david.baseprojectstructure.ui.dismissable.DismissableWatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by david on 2017/3/16.
 */

public abstract class BaseActivity extends AppCompatActivity
    implements IActivity, DismissableWatcher, IView, View.OnClickListener {

  private Map<Integer, PermissionListener> permissionListenerMap = new HashMap<>();
  private AtomicInteger requestCodeNumber = new AtomicInteger(1000);
  private List<Dismissable> dismissableList = new ArrayList<>();
  private List<DisposableWatcher> disposableWatcherList = new ArrayList<>();

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    preSetContentView();

    initStatusBar();

    /** 开始创建视图 **/
    Logger.d(getClass().getSimpleName(), "activity create view start");
    View contentView = createView(savedInstanceState);
    setContentView(contentView);
    Logger.d(getClass().getSimpleName(), "activity create view end");
    /** 创建视图结束 **/

    Logger.d(getClass().getSimpleName(), "activity init start");
    preInit(savedInstanceState);
    initView();
    initListener();
    initData();
    Logger.d(getClass().getSimpleName(), "activity init end");
  }

  protected abstract View createView(@Nullable Bundle savedInstanceState);

  @Override protected void onResume() {
    super.onResume();
    if (isHideBottomUiMenu()) {
      hideBottomUiMenu();
    }
  }

  private void initStatusBar() {
    int visibility = getWindow().getDecorView().getSystemUiVisibility();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (isLightColor(getStatusBarColor())) {
        visibility = visibility | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
      } else {
        visibility = visibility | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
      }
      getWindow().getDecorView().setSystemUiVisibility(visibility);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      visibility = visibility | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
      getWindow().getDecorView().setSystemUiVisibility(visibility);
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      visibility = visibility | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
      getWindow().getDecorView().setSystemUiVisibility(visibility);
      WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
      localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
    }
  }

  public void hideBottomUiMenu() {
    if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
      getWindow().getDecorView().setSystemUiVisibility(View.GONE);
    } else if (Build.VERSION.SDK_INT >= 19) {//for new api versions.
      View decorView = getWindow().getDecorView();
      int uiOptions = decorView.getSystemUiVisibility()
          | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
      decorView.setSystemUiVisibility(uiOptions);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
  }

  private boolean isLightColor(int color) {
    double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
    if (darkness < 0.5) {
      return true; // It's a light color
    } else {
      return false; // It's a dark color
    }
  }

  protected boolean isHideBottomUiMenu() {
    return true;
  }

  protected boolean isStatusBarSuspension() {
    return false;
  }

  protected int getStatusBarColor() {
    return getResources().getColor(R.color.colorPrimary);
  }

  protected int getStatusBarHeight() {
    int statusBarHeight;
    int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
    statusBarHeight = getResources().getDimensionPixelSize(resourceId);
    return statusBarHeight;
  }

  /**
   * 在setContentView之前执行
   */
  protected void preSetContentView() {

  }

  /**
   * 在执行init(initView、initListener、initData)之前执行
   */
  protected void preInit(Bundle savedInstanceState) {

  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      default:
        break;
    }
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
      dismissable.dismiss();
    }
    dismissableList.clear();
  }

  @Override protected void onDestroy() {
    release();
    releaseDismissables();
    for (DisposableWatcher watcher : disposableWatcherList) {
      watcher.releaseDisposables();
    }
    disposableWatcherList.clear();
    super.onDestroy();
  }

  @Override public void onNetworkError(Throwable throwable) {
    if (throwable instanceof NoNetworkException) {
      Toaster.toast("无网络，请检查网络连接");
    } else {
      Toaster.toast(throwable.getMessage());
    }
  }

  /**
   * 请求权限
   */
  public void requestPermission(String permisson, PermissionListener listener) {
    if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, permisson)) {
      if (listener != null) {
        listener.onGranted();
      }
    } else {
      boolean hasDenited = ActivityCompat.shouldShowRequestPermissionRationale(this, permisson);
      if (!hasDenited) {
        int requestCode = requestCodeNumber.getAndIncrement();
        permissionListenerMap.put(requestCode, listener);
        ActivityCompat.requestPermissions(this, new String[] { permisson }, requestCode);
      } else {
        if (listener != null) {
          listener.onNotAsk();
        }
      }
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    int result = grantResults[0];
    PermissionListener listener = permissionListenerMap.get(requestCode);
    if (PackageManager.PERMISSION_GRANTED == result) {
      if (listener != null) {
        listener.onGranted();
      }
    } else {
      if (listener != null) {
        listener.onDenited();
      }
    }
    permissionListenerMap.remove(requestCode);
  }

  /**
   * 权限申请结果监听器
   */
  public interface PermissionListener {
    void onGranted();//权限请求被允许

    void onDenited();//权限请求被拒绝

    void onNotAsk();//不再请求
  }

  protected static void startThis(Context context, Intent intent) {
    context.startActivity(intent);
  }

  protected static void startThis(Context context, Intent intent, ActivityOptionsCompat optionsCompat) {
    ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());
  }

  protected static void startThisWithAnim(Context context, Intent intent, ActivityOptionsCompat optionsCompat) {
    ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());
  }

  protected void finishThis() {
    finish();
  }

  protected void finishThisAfterAnim() {
    ActivityCompat.finishAfterTransition(this);
  }
}
