package com.tvnsoftware.drcare.Utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Admin on 7/27/2017.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int mVerticalSpaceHeight;

    public SpaceItemDecoration(int space) {
        this.mVerticalSpaceHeight = space;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.left = mVerticalSpaceHeight;
        outRect.right = mVerticalSpaceHeight;
        outRect.top = mVerticalSpaceHeight;
        outRect.bottom = mVerticalSpaceHeight;

    }
}
