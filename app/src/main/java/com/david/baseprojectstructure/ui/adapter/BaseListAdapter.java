package com.david.baseprojectstructure.ui.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david on 2017/12/23.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {

  protected List<T> data = new ArrayList<>();

  public void setData(List<T> tList) {
    data.clear();
    if (tList != null) {
      data.addAll(tList);
    }
    notifyDataSetChanged();
  }

  public void addData(T t) {
    data.add(t);
    notifyDataSetChanged();
  }

  public void clearData() {
    data.clear();
    notifyDataSetChanged();
  }

  @Override public int getItemCount() {
    return data.size();
  }
}
