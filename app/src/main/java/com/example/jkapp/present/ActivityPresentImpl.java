package com.example.jkapp.present;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.jkapp.CallBack.NetCallBackImp;
import com.example.jkapp.bean.BaseEneity;
import com.example.jkapp.helper.GenericHelper;
import com.example.jkapp.socketUtils.TcpConnectUtil;
import com.example.jkapp.utils.ActivityControl;
import com.example.jkapp.utils.LogUtil;
import com.example.jkapp.view.IView;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpTaskHandler;

import static com.example.jkapp.utils.Constant.TCPDISLINK;
import static com.example.jkapp.utils.Constant.TCPLINK;
import static com.example.jkapp.utils.Constant.TCPNONET;

/**
 * Created by dufangyu on 2017/6/13.
 */

public  class ActivityPresentImpl<T extends IView>extends AppCompatActivity implements IPresent<T>,HttpCycleContext {

    protected T mView;
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();



    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mReceiver;
    private IntentFilter mFilter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beforeViewCreate(savedInstanceState);
        ActivityControl.addActivity(this);
        try {
            mView = getViewClass().newInstance();
            setContentView(mView.createView(getLayoutInflater(),null));
            mView.bindPresenter(this);
            mView.bindEvent();
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
            TcpConnectUtil.getTcpInstance().setNetCallBack(netCallBackImp);
            TcpConnectUtil.getTcpInstance().startThreads();
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
    public void logicItemClickHandler(BaseEneity eneity) {

    }

    @Override
    public void pullRefresh() {

    }

    @Override
    public void pushLoadMore() {

    }


    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityControl.removeActivity(this);
        //销毁后结束请求
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);
        LogUtil.d("dfy", getClass().getSimpleName() + "  onDestroy");
    }
    @Override
    protected void onPause() {
        super.onPause();
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
                    LogUtil.d("dfy", "无网络");
                    doNoNetWork();
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
        finish();
    }

    //app连接不上服务器(断网或者服务器出问题)
    public void doNetDisConnect(){

    }

    //app连接服务器成功
    public void doNetConnect(){

    }

    //app没网
    public void doNoNetWork(){

    }




}
