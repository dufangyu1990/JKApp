package com.example.jkapp.view;

import android.view.View;
import android.widget.TextView;

import com.example.jkapp.R;
import com.example.jkapp.helper.EventHelper;

/**
 * Created by dufangyu on 2017/9/6.
 */

public class SystemSetView extends ViewImpl{

    private TextView titleText;
    private TextView backTv;

    @Override
    public void initView() {
        titleText = findViewById(R.id.title_text);
        backTv = findViewById(R.id.back_img);
        backTv.setVisibility(View.VISIBLE);
        backTv.setText(mRootView.getContext().getString(R.string.myself));
        titleText.setText(mRootView.getContext().getString(R.string.SystemSet));

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_systemset;
    }

    @Override
    public void bindEvent() {

        EventHelper.click(mPresent,backTv);

    }
}
