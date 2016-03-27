package com.debashis.movieapp.presenter;

import com.android.volley.RequestQueue;
import com.debashis.movieapp.model.ApiCallback;
import com.debashis.movieapp.model.NetworkManager;
import com.debashis.movieapp.model.OmdbMovieApi;
import com.debashis.movieapp.model.volley.MovieVolley;
import com.debashis.movieapp.view.activity.DetailsActivity;
import com.debashis.movieapp.view.activity.SearchActivity;

import org.json.JSONObject;

/**
 * Created by Debashis on 27/3/16.
 */
public class DetailsPresenter implements ApiCallback {
    private PresenterView mPresenterView;
    private RequestQueue mRequestQueue;
    private OmdbMovieApi mMovieApi;

    public DetailsPresenter(PresenterView presenterView){
        this.mPresenterView = presenterView;
        mMovieApi = new OmdbMovieApi(this);
        mRequestQueue = MovieVolley.getInstance((DetailsActivity) mPresenterView).getRequestQueue();
    }

    public void getMovieDetails(String title){
        mPresenterView.showProgressBar();
        mMovieApi.getMovieDetails(title, mRequestQueue);
    }

    public void onDestroy(){
        mPresenterView = null;
    }

    @Override
    public void onSuccessResponse(JSONObject jsonObject) {
        mPresenterView.hideProgressBar();
        mPresenterView.onResponse(jsonObject);
    }

    @Override
    public void onErrorResponse(String errorMessage) {
        mPresenterView.hideProgressBar();
        mPresenterView.onError(errorMessage);
    }
}
