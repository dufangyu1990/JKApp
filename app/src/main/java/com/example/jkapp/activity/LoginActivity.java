package com.example.jkapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jkapp.R;
import com.example.jkapp.biz.ILogin;
import com.example.jkapp.biz.LoginBiz;
import com.example.jkapp.biz.LoginListener;
import com.example.jkapp.customview.CustomDialog;
import com.example.jkapp.present.ActivityPresentImpl;
import com.example.jkapp.socketUtils.TcpConnectUtil;
import com.example.jkapp.utils.MyToast;
import com.example.jkapp.view.LoginView;

import static com.example.jkapp.utils.Constant.TCPDISLINK;
import static com.example.jkapp.utils.Constant.TCPLINK;


/**
 * Created by dufangyu on 2017/8/30.
 */

public class LoginActivity extends ActivityPresentImpl<LoginView> implements View.OnClickListener{

    private long exitTime=0;
    private ILogin loginBiz;
    private boolean isConnected;
    @Override
    public void afterViewCreate(Bundle savedInstanceState) {
        super.afterViewCreate(savedInstanceState);
        loginBiz = new LoginBiz();
        isConnected = getIntent().getBooleanExtra("isConnected",false);
        if(isConnected)
            mView.setNetState(TCPLINK,getResources().getString(R.string.netconnect));
        mView.initPwdCheckBox();

    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.login_button:
                login();
                break;
        }
    }


    public static void actionStart(Context context,boolean isConnected)
    {
        Intent intent  = new Intent(context,LoginActivity.class);
        intent.putExtra("isConnected",isConnected);
        context.startActivity(intent);
    }

    private void login()
    {
        if(!TcpConnectUtil.p_bLinkCenterON)
        {
            MyToast.showTextToast(getApplicationContext(),getResources().getString(R.string.badnetwork));
            return;
        }

        String loginName = mView.getTextValue(R.id.user_name_et);
        String password = mView.getTextValue(R.id.pass_word_et);
        loginBiz.login(loginName, password, new LoginListener() {
            @Override
            public void loginSuccess(String code) {
                mView.saveAccountNdPwd();
                MainActivity.actionStart(LoginActivity.this,true,code);
                finish();
            }

            @Override
            public void loginFailed() {
                CustomDialog.show(LoginActivity.this, getResources().getString(R.string.loginfail), false, null, R.layout.text_dialog);
                CustomDialog.setAutoDismiss(true, 1500);

            }
        });



    }


    public void pressAgainExit(){
        if((System.currentTimeMillis()-exitTime) > 2000){
            MyToast.showTextToast(getApplicationContext(), getResources().getString(R.string.pressagain));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void doNetConnect() {
        super.doNetConnect();
        mView.setNetState(TCPLINK,getResources().getString(R.string.netconnect));

    }


    @Override
    public void doNetDisConnect() {
        super.doNetDisConnect();
        mView.setNetState(TCPDISLINK,getResources().getString(R.string.strLinkStateLabel));
    }
}
