package com.debashis.movieapp.model;

import org.json.JSONObject;

/**
 * Created by Debashis on 26/3/16.
 */
public interface ApiCallback {
    void onSuccessResponse(JSONObject jsonObject);
    void onErrorResponse(String errorMessage);
}
