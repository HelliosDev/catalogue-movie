package com.coding.wk.cataloguemovieextendedapplication.utils;

import android.view.View;

public class CustomOnClickListener implements View.OnClickListener {
    private int position;
    private OnItemClickListener onItemClickListener;

    public CustomOnClickListener(int position, OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
        this.position = position;
    }
    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }
    @Override
    public void onClick(View view) {

        onItemClickListener.onItemClick(view, position);
    }
}
