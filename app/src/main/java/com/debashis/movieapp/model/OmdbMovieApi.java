package com.debashis.movieapp.model;

import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.debashis.movieapp.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Debashis on 26/3/16.
 */
public class OmdbMovieApi {

    private ApiCallback mCallback;
    private static final String TAG = OmdbMovieApi.class.getSimpleName();

    public OmdbMovieApi(ApiCallback callback){
        this.mCallback = callback;
    }

    public void getMovieList(String searchKey, RequestQueue requestQueue){
        StringBuilder builder = new StringBuilder();
        builder.append(BuildConfig.BASE_URL);
        builder.append("s=");
        builder.append(searchKey);

        String url = builder.toString();
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if(error instanceof NoConnectionError){
                            Log.i(TAG, "Error message: " + "No internet connection");
                            mCallback.onErrorResponse("No internet connection...please check your connectivity.");
                        }
                        else
                            mCallback.onErrorResponse("Something wrong occoured, please try again");
                    }
                }
        );

        requestQueue.add(request);
    }

    public void getMovieDetails(String title, RequestQueue requestQueue){
        StringBuilder builder = new StringBuilder();
        builder.append(BuildConfig.BASE_URL);
        builder.append("t=");
        builder.append(title.replace(" ", "+"));

        String url = builder.toString();
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if(error instanceof NoConnectionError){
                            Log.i(TAG, "Error message: " + "No internet connection");
                            mCallback.onErrorResponse("No internet connection...please check your connectivity.");
                        }
                        else
                            mCallback.onErrorResponse("Something wrong occoured, please try again");

                    }
                }
        );

        requestQueue.add(request);
    }

    public void parseResponse(JSONObject response){
        try {
            if(response.getString("Response").equals("True")){
                mCallback.onSuccessResponse(response);
            }
            else if(response.getString("Response").equals("False")){
                String error = response.getString("Error");
                mCallback.onErrorResponse(error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            mCallback.onErrorResponse("Something wrong occoured, please try again");
        }
    }
}
