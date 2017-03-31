package com.david.baseprojectstructure.ui.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by david on 2017/3/28.
 */

public class DataBindingViewHolder extends RecyclerView.ViewHolder {
  private ViewDataBinding binding;

  public DataBindingViewHolder(ViewDataBinding binding) {
    super(binding.getRoot());
    this.binding = binding;
  }

  public ViewDataBinding getBinding() {
    return binding;
  }
}
