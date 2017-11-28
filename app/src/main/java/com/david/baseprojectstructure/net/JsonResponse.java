package com.david.baseprojectstructure.net;

import com.google.gson.annotations.SerializedName;

/**
 * Created by david on 2017/11/24.
 */

public class JsonResponse<T> {

  public static final String CODE = "code";
  public static final String RESPONSE = "response";
  public static final String MSG = "msg";
  public static final String RESPONSE_STRING = "response_string";

  public static final int CODE_SUCCESS = 1;

  @SerializedName(CODE) public int code;
  @SerializedName(MSG) public String msg;
  @SerializedName(RESPONSE) public T response;
  @SerializedName(RESPONSE_STRING) public String responseString;

  public boolean isSuccess() {
    return CODE_SUCCESS == code;
  }
}
