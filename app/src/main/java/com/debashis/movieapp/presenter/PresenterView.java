package com.debashis.movieapp.presenter;

import org.json.JSONObject;

/**
 * Created by Debashis on 26/3/16.
 */
public interface PresenterView {
    public void showProgressBar();
    public void hideProgressBar();
    public void onResponse(JSONObject jsonObject);
    public void onError(String errorMesage);
}
