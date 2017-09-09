package com.vicky7230.eatit.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by agrim on 18/7/17.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private int currentPosition;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected abstract void clear();

    public void onBind(int position) {
        currentPosition = position;
        clear();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }
}
