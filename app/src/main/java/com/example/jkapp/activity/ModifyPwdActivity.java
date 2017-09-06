package com.example.jkapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jkapp.R;
import com.example.jkapp.biz.IModifyPwd;
import com.example.jkapp.biz.ModifyBiz;
import com.example.jkapp.biz.ModifyPwdListener;
import com.example.jkapp.customview.CustomDialog;
import com.example.jkapp.present.ActivityPresentImpl;
import com.example.jkapp.socketUtils.TcpConnectUtil;
import com.example.jkapp.utils.MyToast;
import com.example.jkapp.view.ModifyPwdView;

/**
 * Created by dufangyu on 2017/9/6.
 */

public class ModifyPwdActivity extends ActivityPresentImpl<ModifyPwdView> implements View.OnClickListener{


    private IModifyPwd modifyPwdBiz;
    private String loginName;//登录名
    private String loginPwd;//登录密码
    private String newPassword;//新密码

    @Override
    public void afterViewCreate(Bundle savedInstanceState) {
        super.afterViewCreate(savedInstanceState);
        modifyPwdBiz = new ModifyBiz();
        loginName = getIntent().getStringExtra("login_name");
        loginPwd = getIntent().getStringExtra("pwdStr");

    }

    public  static void actionStart(Context context,String loginName,String loginPwd)
    {
        Intent intent = new Intent(context,ModifyPwdActivity.class);
        intent.putExtra("login_name",loginName);
        intent.putExtra("pwdStr",loginPwd);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_img:
                finish();
                break;
            case R.id.submit_btn:
                modifyPwdAction();
                break;
        }
    }



    private void modifyPwdAction()
    {
        if(!TcpConnectUtil.p_bLinkCenterON)
        {
            MyToast.showTextToast(getApplicationContext(),getResources().getString(R.string.badnetwork));
            return;
        }

        if(mView.checkValid(loginPwd))
        {

            newPassword = mView.getNewPwdStr();

            modifyPwdBiz.modifyPwd(loginName, newPassword, new ModifyPwdListener() {
                @Override
                public void modifySuccess() {
                    CustomDialog.show(ModifyPwdActivity.this, getResources().getString(R.string.modifypwdsuccess), true, null, R.layout.text_dialog);
                    CustomDialog.setAutoDismiss(true,1500,true);
                    MyApplication.getInstance().setStringPerference("Password", newPassword);

                }

                @Override
                public void modifyFail() {
                    CustomDialog.show(ModifyPwdActivity.this, getResources().getString(R.string.modifypwdfail), true, null, R.layout.text_dialog);
                    CustomDialog.setAutoDismiss(true,1500);
                }
            });
        }


    }


}
