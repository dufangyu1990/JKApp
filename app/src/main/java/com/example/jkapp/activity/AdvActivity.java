package com.example.jkapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.jkapp.R;
import com.example.jkapp.biz.AdvBiz;
import com.example.jkapp.biz.AdvListener;
import com.example.jkapp.biz.IAdv;
import com.example.jkapp.customview.CustomLoadDialog;
import com.example.jkapp.present.ActivityPresentImpl;
import com.example.jkapp.socketUtils.TcpConnectUtil;
import com.example.jkapp.utils.Constant;
import com.example.jkapp.utils.LogUtil;
import com.example.jkapp.utils.MyToast;
import com.example.jkapp.view.AdvView;

/**
 * Created by dufangyu on 2017/9/7.
 */

public class AdvActivity extends ActivityPresentImpl<AdvView> implements AdvListener,View.OnClickListener{


    private String loginName;
    private int curIndex = 0;
    private int searchIndex = 0;
    private int searchFlag=0;//搜索标志，0代表用户查询 1代表管理员查询
    private int currentDataType= 1;//当前数据类型(0:搜索出来的数据；1：进入页面返回的数据)
    private int freshType=-1;//0：下拉刷新  1：上拉加载


    private String searchValue = "";
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

        pullRefresh();


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
        if(currentDataType ==1)
        {
            curIndex = 0;
            advBiz.getAdvsFromServer(searchFlag,searchValue,curIndex,Constant.PAGECOUNTNUM,loginName);
        }else if(currentDataType == 0)
        {
            searchIndex = 0;
            advBiz.getAdvsFromServer(searchFlag,searchValue,searchIndex,Constant.PAGECOUNTNUM,loginName);
        }


    }


    //上拉加载
    @Override
    public void pushLoadMore() {
        super.pushLoadMore();
        if(!TcpConnectUtil.p_bLinkCenterON)
        {
            MyToast.showTextToast(AdvActivity.this, "网络连接中断,请检查网络");
            return;
        }

        freshType = 1;
        if(currentDataType==1)
        {
            curIndex++;
            advBiz.getAdvsFromServer(searchFlag,searchValue,curIndex,Constant.PAGECOUNTNUM,loginName);
        }else if(currentDataType == 0)
        {
            searchIndex++;
            advBiz.getAdvsFromServer(searchFlag,searchValue,searchIndex,Constant.PAGECOUNTNUM,loginName);
        }

    }


    @Override
    public void presentCallBack(String param1, String param2, String params3) {
        super.presentCallBack(param1, param2, params3);
        searchValue = params3;

        LogUtil.d("dfy","searchValue = "+searchValue);
        if(TextUtils.isEmpty(searchValue))
        {
            currentDataType = 1;
            searchIndex = 0;
            mView.restoreOldData(currentDataType);
        }else{
            doSearch();
        }

    }


    private void doSearch()
    {
        if(!TcpConnectUtil.p_bLinkCenterON)
        {
            MyToast.showTextToast(AdvActivity.this, "网络连接中断,请检查网络");
            return;
        }

        CustomLoadDialog.show(AdvActivity.this,"",true,null, R.layout.logindialog);
        freshType = 0;
        currentDataType = 0;
        searchIndex = 0;
        advBiz.getAdvsFromServer(searchFlag,searchValue,searchIndex,Constant.PAGECOUNTNUM,loginName);

    }


    @Override
    public void getSocketResult(String[][] strArr, int count) {
        CustomLoadDialog.dismisDialog();
        mView.setData(strArr,count,freshType,currentDataType);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_img:
                finish();
                break;
        }
    }
}
