package com.debashis.movieapp.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.debashis.movieapp.R;
import com.debashis.movieapp.model.MovieTitleSuggestionProvider;
import com.debashis.movieapp.model.NetworkManager;
import com.debashis.movieapp.presenter.PresenterView;
import com.debashis.movieapp.presenter.SearchPresenter;
import com.debashis.movieapp.view.RecyclerItemClickListener;
import com.debashis.movieapp.view.adapters.MovieListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Debashis on 26/3/16.
 */
public class SearchActivity extends AppCompatActivity implements PresenterView, RecyclerItemClickListener.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private RecyclerView.Adapter mAdapter;
    private String mSearchKey;
    private SearchPresenter mSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_layout);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        mSearchPresenter = new SearchPresenter(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.movie_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, this));

        Intent intent = getIntent();
        handleSearchIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchPresenter.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleSearchIntent(intent);
    }

    private void handleSearchIntent(Intent intent){
        if(intent.getAction().equals(Intent.ACTION_SEARCH)){
            mSearchKey = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MovieTitleSuggestionProvider.AUTHORITY, MovieTitleSuggestionProvider.MODE);
            suggestions.saveRecentQuery(mSearchKey, null);

            mSearchPresenter.getMovieList(mSearchKey);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.widget.SearchView searchView = (android.widget.SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQuery(mSearchKey, false);

        return super.onCreateOptionsMenu(menu);
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
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("Search");
            if(jsonArray != null) {
                int count = jsonArray.length();
                Toast.makeText(this,count+" records found", Toast.LENGTH_SHORT).show();

                mAdapter = new MovieListAdapter(jsonArray);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onError(String errorMesage) {
        Toast.makeText(this, errorMesage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(View view, int position) {
        JSONArray jsonArray = ((MovieListAdapter) mAdapter).getJsonArrayMovie();
        try {
            JSONObject jsonObject = jsonArray.getJSONObject(position);
            String title = jsonObject.getString("Title");

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("Movie Title", title);
            startActivity(intent);
            overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.animation_finish_enter, R.anim.animation_finish_leave);
    }
}
