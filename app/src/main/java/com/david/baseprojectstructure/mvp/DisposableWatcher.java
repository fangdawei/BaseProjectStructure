package com.david.baseprojectstructure.mvp;

import io.reactivex.disposables.Disposable;

/**
 * Created by david on 2017/5/5.
 */

public interface DisposableWatcher {

  void registerDisposable(Disposable disposable);

  void unregisterDisposable(Disposable disposable);

  void releaseDisposables();
}
