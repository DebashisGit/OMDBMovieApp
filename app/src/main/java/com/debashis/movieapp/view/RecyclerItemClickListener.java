package com.debashis.movieapp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Debashis on 27/3/16.
 */
public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private OnItemClickListener mClickListener;
    private GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, OnItemClickListener listener){
        this.mClickListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent event) {
        View childView = recyclerView.findChildViewUnder(event.getX(), event.getY());
        if(childView != null && mClickListener != null && mGestureDetector.onTouchEvent(event)){
            int position = recyclerView.getChildAdapterPosition(childView);
            mClickListener.onItemClick(childView, position);
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
