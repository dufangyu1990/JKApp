package com.example.jkapp.customview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dufangyu on 2017/5/11.
 */

public class SpaceItemDecoratio extends RecyclerView.ItemDecoration{
    private int space;

    public SpaceItemDecoratio(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


        outRect.top = space;


//        outRect.left = space;
//        outRect.right = space;
//        outRect.bottom = space;
    }

}
