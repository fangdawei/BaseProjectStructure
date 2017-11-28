package com.david.baseprojectstructure.mvp;

/**
 * Created by david on 2017/11/24.
 */

public interface ErrorListener<T extends Throwable> {
  void onError(T t);
}
