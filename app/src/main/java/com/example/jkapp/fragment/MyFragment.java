package com.example.jkapp.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.jkapp.R;
import com.example.jkapp.activity.LoginActivity;
import com.example.jkapp.activity.MyApplication;
import com.example.jkapp.biz.IMyFragment;
import com.example.jkapp.biz.MyFragmentBiz;
import com.example.jkapp.listener.JumpToActivityListener;
import com.example.jkapp.manager.DataManager;
import com.example.jkapp.present.FragmentPresentImpl;
import com.example.jkapp.socketUtils.TcpConnectUtil;
import com.example.jkapp.utils.ActivityControl;
import com.example.jkapp.utils.MyAlertDialog;
import com.example.jkapp.utils.MyToast;
import com.example.jkapp.view.MyView;

/**
 * Created by dufangyu on 2017/8/31.
 */

public class MyFragment  extends FragmentPresentImpl<MyView> implements View.OnClickListener{

    private String username;
    private String depName;
    private String password;
    private JumpToActivityListener mListener;
    private MyAlertDialog dialog;
    private Context context;
    private IMyFragment myFragmentBiz;

    @Override
    public void afterViewCreate(Bundle savedInstanceState) {
        super.afterViewCreate(savedInstanceState);
        context = getActivity();
        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            username = bundle.getString("username");
            password = bundle.getString("password");
            depName = DataManager.p_strDepList[0][2];
            mView.setValue(R.id.my_name,username);
            mView.setValue(R.id.my_departname,depName);

        }
        myFragmentBiz = new MyFragmentBiz();
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
                mListener.jumpToModifyPawActivity(username,password);
                break;
            case R.id.returnavs_rel:
                mListener.jumpToReturnAdvActivity(username);
                break;
            case R.id.about_rel:
                mListener.jumpToAboutActivity();
                break;
            case R.id.exitlogin:
                showExitDialog();
                break;
        }
    }



    public void showExitDialog()
    {
        dialog = new MyAlertDialog(context).builder().setTitle(context.getResources().getString(R.string.exittitle))
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!TcpConnectUtil.p_bLinkCenterON)
                        {
                            MyToast.showTextToast(context,getResources().getString(R.string.badnetwork));
                            return;
                        }
                        myFragmentBiz.exitApp();
                        MyApplication.getInstance().setStringPerference("isFirst", "YES");
                        LoginActivity.actionStart(context,true);
                        ActivityControl.finishAll();

                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        dialog.show();
    }


}
