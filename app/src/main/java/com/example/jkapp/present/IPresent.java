package com.example.jkapp.present;

import android.os.Bundle;

import com.example.jkapp.bean.BaseEneity;

/**
 * Created by dufangyu on 2017/6/13.
 */

public interface IPresent<T> {

    Class<T> getViewClass();

    void beforeViewCreate(Bundle savedInstanceState);

    void afterViewCreate(Bundle savedInstanceState);



    //view回调activity中方法
    void presentCallBack(String param1, String param2, String params3) ;




    //单击item逻辑
    void logicItemClickHandler(BaseEneity eneity);

    //下拉刷新
    void pullRefresh();

    //上拉加载
    void pushLoadMore();




}
