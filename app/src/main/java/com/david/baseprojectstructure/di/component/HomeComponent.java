package com.david.baseprojectstructure.di.component;

import com.david.baseprojectstructure.di.module.HomeModule;
import com.david.baseprojectstructure.ui.avtivity.HomeActivity;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by david on 2017/3/31.
 */

@Singleton @Component(modules = HomeModule.class)
public interface HomeComponent {

  void inject(HomeActivity activity);
}
