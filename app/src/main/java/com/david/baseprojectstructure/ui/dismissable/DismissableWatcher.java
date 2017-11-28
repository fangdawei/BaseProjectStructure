package com.david.baseprojectstructure.ui.dismissable;

/**
 * Created by david on 2017/11/16.
 */

public interface DismissableWatcher {

  void registerDismissable(Dismissable dismissable);

  void unregisterDismissable(Dismissable dismissable);

  void releaseDismissables();
}
