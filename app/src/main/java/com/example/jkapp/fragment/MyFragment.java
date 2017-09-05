package com.example.jkapp.fragment;


import android.os.Bundle;

import com.example.jkapp.R;
import com.example.jkapp.manager.DataManager;
import com.example.jkapp.present.FragmentPresentImpl;
import com.example.jkapp.utils.LogUtil;
import com.example.jkapp.view.MyView;

/**
 * Created by dufangyu on 2017/8/31.
 */

public class MyFragment  extends FragmentPresentImpl<MyView> {

    private String username;
    private String depName;


    @Override
    public void afterViewCreate(Bundle savedInstanceState) {
        super.afterViewCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            username = bundle.getString("username");
            LogUtil.d("dfy","DataManager.p_strDepList[0][2] = "+DataManager.p_strDepList[0][2]);
            depName = DataManager.p_strDepList[0][2];
            mView.setValue(R.id.my_name,username);
            mView.setValue(R.id.my_departname,depName);

        }
    }
}
