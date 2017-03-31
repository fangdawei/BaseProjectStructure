package com.david.baseprojectstructure.mvp.presenter;

import com.david.baseprojectstructure.mvp.contract.HomeContract;

/**
 * Created by david on 2017/3/29.
 */

public class HomePresenter implements HomeContract.Presenter {

  private HomeContract.View view;
  private HomeContract.Model model;

  public HomePresenter(HomeContract.View view, HomeContract.Model model) {
    this.view = view;
    this.model = model;
  }
}
