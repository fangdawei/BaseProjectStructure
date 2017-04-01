package com.david.baseprojectstructure.ui.avtivity;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.david.baseprojectstructure.R;
import com.david.baseprojectstructure.databinding.ActivityBaseBinding;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by david on 2017/3/16.
 */

public abstract class BaseActivity
    extends AppCompatActivity implements IActivity {

  private ActivityBaseBinding baseVDB;
  private Map<Integer, PermissionListener> permissionListenerMap = new HashMap<>();

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

  protected void requestPermission(String permisson,
      PermissionListener listener, int requestCode) {
    if (PackageManager.PERMISSION_GRANTED ==
        ContextCompat.checkSelfPermission(this, permisson)) {
      if (listener != null) {
        listener.onGranted();
      }
    } else {
      boolean hasDenited = ActivityCompat
          .shouldShowRequestPermissionRationale(this, permisson);
      if (!hasDenited) {
        permissionListenerMap.put(requestCode, listener);
        ActivityCompat.requestPermissions(this,
            new String[] { permisson }, requestCode);
      } else {
        if (listener != null) {
          listener.onNotAsk();
        }
      }
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode,
      @NonNull String[] permissions, @NonNull int[] grantResults) {
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
  }

  protected abstract View createToolBar(Bundle savedInstanceState);

  protected abstract View createContentView(Bundle savedInstanceState);

  interface PermissionListener {
    void onGranted();//权限请求被允许
    void onDenited();//权限请求被拒绝
    void onNotAsk();//不再请求
  }
}
