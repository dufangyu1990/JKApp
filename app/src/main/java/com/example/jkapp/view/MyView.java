package com.example.jkapp.view;


import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jkapp.R;
import com.example.jkapp.helper.EventHelper;

/**
 * Created by dufangyu on 2017/8/31.
 */

public class MyView extends ViewImpl{


    private TextView usernametv;
    private TextView depnametv;
    private RelativeLayout sytem_layout,changepwd_layout,yijian_layout,about_layout;
    private TextView exitlogin;


    @Override
    public void initView() {
        usernametv = findViewById(R.id.my_name);
        depnametv = findViewById(R.id.my_departname);
        sytem_layout = findViewById(R.id.systemset_rel);
        changepwd_layout = findViewById(R.id.changepwd_rel);
        yijian_layout = findViewById(R.id.returnavs_rel);
        about_layout = findViewById(R.id.about_rel);
        exitlogin = findViewById(R.id.exitlogin);

    }




    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void bindEvent() {

        EventHelper.click(mPresent,sytem_layout,changepwd_layout,yijian_layout,about_layout,exitlogin);
    }


    public void setValue(int id,String value)
    {
        if(id==R.id.my_name)
        {
            usernametv.setText(value);
        }else if(id==R.id.my_departname)
        {
            depnametv.setText(value);
        }
    }




}
