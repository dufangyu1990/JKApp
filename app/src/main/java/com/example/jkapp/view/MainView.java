package com.example.jkapp.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.jkapp.R;
import com.example.jkapp.activity.MyApplication;
import com.example.jkapp.fragment.FixcarPageFragment;
import com.example.jkapp.fragment.MyFragment;
import com.example.jkapp.fragment.SearchPageFragment;
import com.example.jkapp.fragment.TongjiPageFragment;

/**
 * Created by dufangyu on 2017/8/31.
 */

public class MainView extends ViewImpl{

    private RadioGroup menuArry;

    private SearchPageFragment searchPageFragment;
    private TongjiPageFragment tongjiPageFragment;
    private FixcarPageFragment fixcarPageFragment;
    private MyFragment myFragment;
    private TextView mTitleText;
    @Override
    public void initView() {
        mTitleText = findViewById(R.id.title_text);
        menuArry = findViewById(R.id.menu_arr);
    }




    public void initTabs(final FragmentManager fm)
    {
        menuArry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                FragmentTransaction transaction = fm.beginTransaction();
                hideFragments(transaction);
                switch (i) {

                    case R.id.tab_search:
                        if (searchPageFragment == null) {
                            searchPageFragment = new SearchPageFragment();
                            transaction.add(R.id.ll_content, searchPageFragment);
                        } else {
                            transaction.show(searchPageFragment);
                        }
                        mTitleText.setText(mRootView.getContext().getString(R.string.findcar));
                        break;
                    case R.id.tab_tongji:
                        if (tongjiPageFragment == null) {
                            tongjiPageFragment = new TongjiPageFragment();
                            transaction.add(R.id.ll_content, tongjiPageFragment);
                        } else {
                            transaction.show(tongjiPageFragment);
                        }
                        mTitleText.setText(mRootView.getContext().getString(R.string.tongji));
                        break;
                    case R.id.tab_fixcar:
                        if (fixcarPageFragment == null) {
                            fixcarPageFragment = new FixcarPageFragment();
                            transaction.add(R.id.ll_content, fixcarPageFragment);
                        } else {
                            transaction.show(fixcarPageFragment);
                        }
                        mTitleText.setText(mRootView.getContext().getString(R.string.fixcar));
                        break;
                    case R.id.tab_myself:
                        if (myFragment == null) {
                            myFragment = new MyFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("username", MyApplication.getInstance().getStringPerference("UserName"));
                            myFragment.setArguments(bundle);
                            transaction.add(R.id.ll_content, myFragment);
                        } else {
                            transaction.show(myFragment);
                        }
                        mTitleText.setText(mRootView.getContext().getString(R.string.myself));
                        break;

                }
                transaction.commit();
            }
        });

        ((RadioButton) menuArry.findViewById(R.id.tab_search)).setChecked(true);
    }


    private void hideFragments(FragmentTransaction ft)
    {

        if(searchPageFragment!=null)
            ft.hide(searchPageFragment);
        if(tongjiPageFragment!=null)
            ft.hide(tongjiPageFragment);
        if(fixcarPageFragment!=null)
            ft.hide(fixcarPageFragment);
        if(myFragment!=null)
            ft.hide(myFragment);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void bindEvent() {

    }
}
