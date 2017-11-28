package com.david.baseprojectstructure.mvp;

import com.david.baseprojectstructure.net.JsonResponse;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by david on 2017/5/5.
 */

public class ObservableWrapper<T> {
  private Disposable disposable;
  private Observable<JsonResponse<T>> observable;
  private DisposableWatcher watcher;

  public ObservableWrapper(Observable<JsonResponse<T>> observable) {
    this.observable = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
  }

  public ObservableWrapper<T> subscribe() {
    setCallback(null, null, null);
    return this;
  }

  public ObservableWrapper<T> subscribe(final SuccessListener<? super T> success) {
    setCallback(success, null, null);
    return this;
  }

  public ObservableWrapper<T> subscribe(final SuccessListener<? super T> success, final FailtureListener failture) {
    setCallback(success, failture, null);
    return this;
  }

  public ObservableWrapper<T> subscribe(final SuccessListener<? super T> success, final FailtureListener failture,
      final ErrorListener<? super Throwable> error) {
    setCallback(success, failture, error);
    return this;
  }

  private void setCallback(final SuccessListener<? super T> success, final FailtureListener failture,
      final ErrorListener<? super Throwable> error) {
    if (observable == null) {
      return;
    }
    disposable = observable.subscribe(result -> {
      T response = result.response;
      if (result.isSuccess()) {
        if (success != null) {
          success.onSuccess(response);
        }
      } else {
        if (failture != null) {
          failture.onFail(result.code, result.msg, result.responseString);
        }
      }
    }, throwable -> {
      if (error != null) {
        error.onError(throwable);
      }
      if (watcher != null) {
        watcher.unregisterDisposable(disposable);
        watcher = null;
      }
    }, () -> {
      if (watcher != null) {
        watcher.unregisterDisposable(disposable);
        watcher = null;
      }
    });
  }

  public void registerTo(DisposableWatcher watcher) {
    if (disposable != null) {
      watcher.registerDisposable(disposable);
      this.watcher = watcher;
    }
  }
}
