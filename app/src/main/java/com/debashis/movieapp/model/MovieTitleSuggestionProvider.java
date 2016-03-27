package com.debashis.movieapp.model;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Debashis on 26/3/16.
 */
public class MovieTitleSuggestionProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = "com.debashis.movieapp.MovieTitleSuggestionProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public MovieTitleSuggestionProvider(){
        setupSuggestions(AUTHORITY, MODE);
    }
}
