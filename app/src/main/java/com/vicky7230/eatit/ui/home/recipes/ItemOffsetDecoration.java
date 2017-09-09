package com.vicky7230.eatit.ui.home.recipes;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by agrim on 12/8/17.
 */

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    private int offset;

    public ItemOffsetDecoration(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();

        if (layoutParams.getSpanIndex() % 2 == 0) {

            outRect.top = offset;
            outRect.left = offset;
            outRect.right = offset / 2;

        } else {

            outRect.top = offset;
            outRect.right = offset;
            outRect.left = offset / 2;

        }

    }
}