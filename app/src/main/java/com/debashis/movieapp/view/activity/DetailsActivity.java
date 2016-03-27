package com.debashis.movieapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.debashis.movieapp.R;
import com.debashis.movieapp.model.volley.MovieVolley;
import com.debashis.movieapp.presenter.DetailsPresenter;
import com.debashis.movieapp.presenter.PresenterView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Debashis on 26/3/16.
 */
public class DetailsActivity extends AppCompatActivity implements PresenterView {
    private NetworkImageView mImageView;
    private TextView mTitle;
    private TextView mReleaseDate;
    private TextView mPlot;
    private TextView mRatings;
    private ProgressBar mProgressBar;
    private ImageLoader mImageLoader;
    private String mMovieTitle;
    private DetailsPresenter mDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_layout);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        mImageLoader = MovieVolley.getInstance(this).getImageLoader();
        mDetailsPresenter = new DetailsPresenter(this);

        mImageView = (NetworkImageView) findViewById(R.id.poster_image);
        mTitle = (TextView) findViewById(R.id.movie_title);
        mReleaseDate = (TextView) findViewById(R.id.movie_release_date);
        mPlot = (TextView) findViewById(R.id.movie_plot);
        mRatings = (TextView) findViewById(R.id.movie_rating);
        mProgressBar = (ProgressBar) findViewById(R.id.details_progress_bar);

        Intent intent = getIntent();
        mMovieTitle = intent.getStringExtra("Movie Title");

        handleIntent(mMovieTitle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDetailsPresenter.onDestroy();
    }

    private void handleIntent(String movieTitle) {
        if(mDetailsPresenter != null){
            mDetailsPresenter.getMovieDetails(movieTitle);
        }
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if(jsonObject != null) {
            String posterUrl = "";
            String title = "";
            String releaseDate = "";
            String plot = "";
            String imdbRating = "";
            try {
                posterUrl = jsonObject.getString("Poster");
                title = jsonObject.getString("Title");
                releaseDate = jsonObject.getString("Released");
                plot = jsonObject.getString("Plot");
                imdbRating = jsonObject.getString("imdbRating");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(!(posterUrl.equals("") || posterUrl.equals(null))){
                mImageView.setImageUrl(posterUrl, mImageLoader);
            }

            if(!(title.equals("") || title.equals(null))){
                title = "Title: " + title;
                mTitle.setText(title);
            }

            if(!(releaseDate.equals("") || releaseDate.equals(null))){
                releaseDate = "Release date: " + releaseDate;
                mReleaseDate.setText(releaseDate);
            }

            if(!(plot.equals("") || plot.equals(null))){
                mPlot.setText(plot);
            }

            if(!(imdbRating.equals("") || imdbRating.equals(null))){
                imdbRating = "IMDB Rating: " + imdbRating;
                mRatings.setText(imdbRating);
            }

        }
    }

    @Override
    public void onError(String errorMesage) {
        Toast.makeText(this, errorMesage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.animation_finish_enter, R.anim.animation_finish_leave);
    }
}
