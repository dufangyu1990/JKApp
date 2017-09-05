package com.example.jkapp.view;


import android.widget.TextView;

import com.example.jkapp.R;

/**
 * Created by dufangyu on 2017/8/31.
 */

public class MyView extends ViewImpl{


    private TextView usernametv;
    private TextView depnametv;
    @Override
    public void initView() {
        usernametv = findViewById(R.id.my_name);
        depnametv = findViewById(R.id.my_departname);
    }




    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void bindEvent() {

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
