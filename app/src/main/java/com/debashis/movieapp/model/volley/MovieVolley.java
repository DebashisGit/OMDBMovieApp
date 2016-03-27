package com.debashis.movieapp.model.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Debashis on 26/3/16.
 */
public class MovieVolley {

    private static MovieVolley mMovieVolley;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    private static Context mContext;

    private MovieVolley(Context context){
        this.mContext = context.getApplicationContext();
        requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(getRequestQueue(), new LruBitmapCache(LruBitmapCache.getCacheSize()));
    }

    public static synchronized MovieVolley getInstance(Context context){
        if(mMovieVolley == null){
            mMovieVolley = new MovieVolley(context);
        }

        return mMovieVolley;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(mContext);
        }

        return requestQueue;
    }

    public ImageLoader getImageLoader(){
        return imageLoader;
    }
}
