package com.example.jkapp.fragment;


import android.view.View;

import com.example.jkapp.R;
import com.example.jkapp.present.FragmentPresentImpl;
import com.example.jkapp.view.FixPageView;

/**
 * Created by dufangyu on 2017/8/31.
 */

public class FixcarPageFragment extends FragmentPresentImpl<FixPageView> implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.next_one_btn:


                mView.changeLayoutRes(R.id.next_one_btn);
                break;
            case R.id.pre_button:
                mView.changeLayoutRes(R.id.pre_button);
                break;
            case R.id.pre_button_long:
                mView.changeLayoutRes(R.id.pre_button_long);
                break;
            case R.id.next_two_btn:
                mView.changeLayoutRes(R.id.next_two_btn);
                break;
            case R.id.pre_three_button:
                mView.changeLayoutRes(R.id.pre_three_button);
                break;
            case R.id.liandongscantv:
                break;
            case R.id.putongscantv:
                break;
        }
    }
}
