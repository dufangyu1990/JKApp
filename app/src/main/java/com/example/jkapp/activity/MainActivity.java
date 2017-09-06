package com.example.jkapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.jkapp.R;
import com.example.jkapp.biz.IMain;
import com.example.jkapp.biz.MainBiz;
import com.example.jkapp.biz.MainListener;
import com.example.jkapp.listener.JumpToActivityListener;
import com.example.jkapp.present.FragmentActivityPresentImpl;
import com.example.jkapp.utils.MyToast;
import com.example.jkapp.view.MainView;

public class MainActivity extends FragmentActivityPresentImpl<MainView> implements MainListener,JumpToActivityListener{

    private long exitTime=0;

    private boolean fromlogin;
    private IMain mainBiz;
    private String depCode;
    private String strShareUserName;
    private String strSharePassword;
    @Override
    public void afterViewCreate(Bundle savedInstanceState) {
        super.afterViewCreate(savedInstanceState);
        mainBiz = new MainBiz(this);
        FragmentManager manager = getSupportFragmentManager();
        mView.initTabs(manager);
        fromlogin = getIntent().getBooleanExtra("fromlogin",false);
        depCode = getIntent().getStringExtra("depCode");
        strShareUserName = MyApplication.getInstance().getStringPerference("UserName");
        strSharePassword =MyApplication.getInstance().getStringPerference("Password");
        if(!fromlogin)
        {
            mainBiz.sendLoginCommad(strShareUserName, strSharePassword);
        }
        mainBiz.getDepResult(depCode, strShareUserName);

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


    /**
     *
     * @param context
     * @param fromlogin 是否是从登录界面进来  true  是   false 不是
     * false 的时候需要发送一个登陆登录指令
     */
    public static void actionStart(Context context,boolean fromlogin)
    {
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("fromlogin",fromlogin);
        context.startActivity(intent);
    }

    public static void actionStart(Context context,boolean fromlogin,String code)
    {
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("fromlogin",fromlogin);
        intent.putExtra("depCode",code);
        context.startActivity(intent);
    }


    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFailed() {

    }

    @Override
    public void jumpToSystemSetActivity() {
        SystemSetActivity.actionStart(this,strShareUserName);
    }
}
