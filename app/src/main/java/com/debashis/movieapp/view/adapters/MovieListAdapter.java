package com.debashis.movieapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.debashis.movieapp.R;
import com.debashis.movieapp.model.volley.MovieVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Debashis on 26/3/16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {


    private JSONArray jsonArrayMovie;
    private ImageLoader imageLoader;

    public MovieListAdapter(JSONArray jsonArray){
        this.jsonArrayMovie = jsonArray;
    }

    public JSONArray getJsonArrayMovie() {
        return jsonArrayMovie;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        imageLoader = MovieVolley.getInstance(parent.getContext()).getImageLoader();
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return jsonArrayMovie.length();
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        try {
            JSONObject jsonObject = jsonArrayMovie.getJSONObject(position);
            String title = jsonObject.getString("Title");
            String url = jsonObject.getString("Poster");

            holder.movieNameText.setText(title);
            holder.imageView.setImageUrl(url, imageLoader);
        }
        catch(JSONException ex){
            ex.printStackTrace();
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public NetworkImageView imageView;
        public TextView movieNameText;

        public MovieViewHolder(View itemView) {
            super(itemView);

            imageView = (NetworkImageView) itemView.findViewById(R.id.movie_image);
            movieNameText = (TextView) itemView.findViewById(R.id.movie_name);
        }
    }
}
