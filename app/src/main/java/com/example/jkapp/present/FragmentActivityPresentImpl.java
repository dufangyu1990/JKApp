package com.example.jkapp.present;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.example.jkapp.CallBack.NetCallBackImp;
import com.example.jkapp.R;
import com.example.jkapp.helper.GenericHelper;
import com.example.jkapp.socketUtils.TcpConnectUtil;
import com.example.jkapp.utils.ActivityControl;
import com.example.jkapp.utils.LogUtil;
import com.example.jkapp.utils.MyToast;
import com.example.jkapp.view.IView;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpTaskHandler;

import static com.example.jkapp.utils.Constant.TCPDISLINK;
import static com.example.jkapp.utils.Constant.TCPLINK;
import static com.example.jkapp.utils.Constant.TCPNONET;

/**
 * Created by dufangyu on 2017/6/13.
 */

public class FragmentActivityPresentImpl<T extends IView>extends FragmentActivity implements IPresent<T>,HttpCycleContext {

    protected T mView;
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeViewCreate(savedInstanceState);
        ActivityControl.addActivity(this);
        try {
            mView = getViewClass().newInstance();
            mView.bindPresenter(this);
            setContentView(mView.createView(getLayoutInflater(),null));
            mView.bindEvent();
            TcpConnectUtil.getTcpInstance().setNetCallBack(netCallBackImp);
            afterViewCreate(savedInstanceState);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<T> getViewClass() {
        return GenericHelper.getViewClass(getClass());
    }

    @Override
    public void beforeViewCreate(Bundle savedInstanceState) {

    }

    @Override
    public void afterViewCreate(Bundle savedInstanceState) {

    }

    @Override
    public void presentCallBack(String param1, String param2, String params3) {

    }


    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityControl.removeActivity(this);
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
        {
            pressAgainExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private NetCallBackImp netCallBackImp =new NetCallBackImp() {
        @Override
        public void runOnUI(int stateCode) {
            switch (stateCode)
            {
                case TCPNONET:
                    MyToast.showTextToast(getApplicationContext(),getResources().getString(R.string.badnetwork));
                    break;
                case TCPDISLINK:
                    LogUtil.d("dfy", "与服务器连接断开");
                    doNetDisConnect();
                    break;
                case TCPLINK:
                    LogUtil.d("dfy","与服务器连接成功");
                    doNetConnect();
                    break;

            }
        }
    };


    public void pressAgainExit(){

    }

    public void doNetDisConnect(){

    }

    public void doNetConnect(){

    }
}
