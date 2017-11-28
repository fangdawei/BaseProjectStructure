package com.david.baseprojectstructure.ui.dismissable;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

/**
 * Created by david on 2017/3/23.
 */

public class BaseDialog extends Dialog {

  public BaseDialog(@NonNull Context context) {
    super(context);
  }

  public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
    super(context, themeResId);
  }

  protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
    super(context, cancelable, cancelListener);
  }
}
