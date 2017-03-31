package com.david.baseprojectstructure.ui.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.concurrent.TimeUnit;

/**
 * Created by david on 2017/3/30.
 */

public class SplashActivity extends AppCompatActivity {

  public static final String TAG = "SplashActivity";

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Observable.timer(2000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread(), true).subscribe(time -> {
      HomeActivity.startActivity(this);
      this.finish();
    });
  }
}
