package com.david.baseprojectstructure.di.module;

import com.david.baseprojectstructure.mvp.contract.HomeContract;
import com.david.baseprojectstructure.mvp.model.HomeModel;
import com.david.baseprojectstructure.mvp.presenter.HomePresenter;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by david on 2017/3/31.
 */

@Module public class HomeModule {

  private HomeContract.View view;

  public HomeModule(HomeContract.View view) {
    this.view = view;
  }

  @Provides @Singleton HomeContract.Model provideModel() {
    return new HomeModel();
  }

  @Provides @Singleton HomeContract.View provideView() {
    return view;
  }

  @Provides @Singleton HomeContract.Presenter providePresenter(HomeContract.View view, HomeContract.Model model) {
    return new HomePresenter(view, model);
  }
}
