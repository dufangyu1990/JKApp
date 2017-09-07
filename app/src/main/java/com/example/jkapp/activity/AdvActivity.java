package com.example.jkapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.jkapp.biz.AdvBiz;
import com.example.jkapp.biz.AdvListener;
import com.example.jkapp.biz.IAdv;
import com.example.jkapp.present.ActivityPresentImpl;
import com.example.jkapp.socketUtils.TcpConnectUtil;
import com.example.jkapp.utils.Constant;
import com.example.jkapp.utils.LogUtil;
import com.example.jkapp.utils.MyToast;
import com.example.jkapp.view.AdvView;

/**
 * Created by dufangyu on 2017/9/7.
 */

public class AdvActivity extends ActivityPresentImpl<AdvView> implements AdvListener{


    private String loginName;
    private int curIndex = 0;
    private int searchIndex = 0;
    private int searchFlag=0;//搜索标志，0代表用户查询 1代表管理员查询
    private int currentDataType= -1;//当前数据类型(0:搜索出来的数据；1：进入页面返回的数据)
    private int freshType=-1;//0：下拉刷新  1：上拉加载


    private IAdv advBiz;

    @Override
    public void afterViewCreate(Bundle savedInstanceState) {
        super.afterViewCreate(savedInstanceState);
        loginName = getIntent().getStringExtra("loginName");

        if(Constant.MANAGER.equals(loginName))
        {
            searchFlag =1;
        }else{
            searchFlag =0;
        }

        advBiz = new AdvBiz(this);
    }

    public static void actionStart(Context context, String loginName) {
        Intent intent = new Intent(context,AdvActivity.class);
        intent.putExtra("loginName",loginName);
        context.startActivity(intent);
    }


    //下拉刷新
    @Override
    public void pullRefresh() {
        super.pullRefresh();

        LogUtil.d("dfy","pullRefresh");
        if(!TcpConnectUtil.p_bLinkCenterON)
        {
            MyToast.showTextToast(AdvActivity.this, "网络连接中断,请检查网络");
            return;
        }
        freshType = 0;
        currentDataType = 1;
        curIndex = 0;
        advBiz.getAdvsFromServer(searchFlag,curIndex,Constant.PAGECOUNTNUM,loginName);
    }


    //上拉加载
    @Override
    public void pushLoadMore() {
        super.pushLoadMore();
    }

    @Override
    public void getSocketResult(String[][] strArr, int count) {
        mView.setData(strArr,count,freshType,currentDataType);

    }
}
