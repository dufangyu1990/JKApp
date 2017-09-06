package com.example.jkapp.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.jkapp.R;
import com.example.jkapp.listener.JumpToActivityListener;
import com.example.jkapp.manager.DataManager;
import com.example.jkapp.present.FragmentPresentImpl;
import com.example.jkapp.view.MyView;

/**
 * Created by dufangyu on 2017/8/31.
 */

public class MyFragment  extends FragmentPresentImpl<MyView> implements View.OnClickListener{

    private String username;
    private String depName;
    private JumpToActivityListener mListener;


    @Override
    public void afterViewCreate(Bundle savedInstanceState) {
        super.afterViewCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            username = bundle.getString("username");
            depName = DataManager.p_strDepList[0][2];
            mView.setValue(R.id.my_name,username);
            mView.setValue(R.id.my_departname,depName);

        }
    }



    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (JumpToActivityListener)activity;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.systemset_rel:
                mListener.jumpToSystemSetActivity();
                break;
            case R.id.changepwd_rel:
                break;
            case R.id.returnavs_rel:
                break;
            case R.id.about_rel:
                break;
            case R.id.exitlogin:
                break;
        }
    }
}
