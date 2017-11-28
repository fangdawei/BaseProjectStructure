package com.david.baseprojectstructure.mvp.presenter;

import com.david.baseprojectstructure.mvp.Cancelable;
import com.david.baseprojectstructure.mvp.DisposableWatcher;
import com.david.baseprojectstructure.mvp.TimeListener;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import java.util.concurrent.TimeUnit;

/**
 * Created by david on 2017/10/9.
 */

public class BasePresenter implements DisposableWatcher {

  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  @Override public void registerDisposable(Disposable disposable) {
    if (disposable == null) {
      return;
    }
    compositeDisposable.add(disposable);
  }

  @Override public void unregisterDisposable(Disposable disposable) {
    if (disposable == null) {
      return;
    }
    compositeDisposable.remove(disposable);
  }

  @Override public void releaseDisposables() {
    compositeDisposable.dispose();
  }

  public Cancelable timer(long delay, final TimeListener listener) {
    TimeCancelable timeCancelable = new TimeCancelable();
    Observable.timer(delay, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Long>() {
          Disposable disposable;

          @Override public void onSubscribe(Disposable d) {
            disposable = d;
            registerDisposable(disposable);
            timeCancelable.setDisposable(disposable);
          }

          @Override public void onNext(Long aLong) {
            if (listener != null) {
              listener.onTime();
            }
          }

          @Override public void onError(Throwable e) {
            cancel(disposable);
            unregisterDisposable(disposable);
          }

          @Override public void onComplete() {
            cancel(disposable);
            unregisterDisposable(disposable);
          }
        });
    return timeCancelable;
  }

  public Cancelable interval(long period, final TimeListener listener) {
    TimeCancelable timeCancelable = new TimeCancelable();
    Observable.interval(period, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<Long>() {
          Disposable disposable;

          @Override public void onSubscribe(Disposable d) {
            disposable = d;
            registerDisposable(disposable);
            timeCancelable.setDisposable(disposable);
          }

          @Override public void onNext(Long aLong) {
            if(listener != null){
              listener.onTime();
            }
          }

          @Override public void onError(Throwable e) {
            cancel(disposable);
            unregisterDisposable(disposable);
          }

          @Override public void onComplete() {
            cancel(disposable);
            unregisterDisposable(disposable);
          }
        });
    return timeCancelable;
  }

  private void cancel(Disposable disposable) {
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }

  class TimeCancelable implements Cancelable {
    Disposable disposable;

    public void setDisposable(Disposable disposable) {
      this.disposable = disposable;
    }

    @Override public void cancel() {
      BasePresenter.this.cancel(disposable);
      unregisterDisposable(disposable);
    }
  }
}
