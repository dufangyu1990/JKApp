package com.example.jkapp.view;

import com.example.jkapp.R;
import com.example.jkapp.adapter.RecycleViewAdapter;
import com.example.jkapp.bean.Question;
import com.example.jkapp.customview.CustomDialog;
import com.example.jkapp.utils.Constant;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;

/**
 * Created by dufangyu on 2017/9/7.
 */

public class AdvView extends ViewImpl implements PullLoadMoreRecyclerView.PullLoadMoreListener{
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private ArrayList<Question> dataList = new ArrayList<Question>();// 本界面需要用的list
    private ArrayList<Question> mSearchList = new ArrayList<Question>();// 搜索框的数据list
    private RecycleViewAdapter mRecyclerViewAdapter;
    @Override
    public void initView() {
        pullLoadMoreRecyclerView = findViewById(R.id.pullLoadMoreRecyclerView);

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        //是否显示下拉刷新
        pullLoadMoreRecyclerView.setRefreshing(true);
        //设置上拉加载文字
//        pullLoadMoreRecyclerView.setFooterViewText("loading");
        //设置上拉加载文字颜色
        //mPullLoadMoreRecyclerView.setFooterViewTextColor(R.color.white);
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        //允许下拉刷新
        pullLoadMoreRecyclerView.setPullRefreshEnable(true);
        //允许上拉加载
        pullLoadMoreRecyclerView.setPushRefreshEnable(true);
        pullLoadMoreRecyclerView.setLinearLayout();
        mRecyclerViewAdapter = new RecycleViewAdapter();
        pullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
//        int spacingInPixels = mRootView.getContext().getResources().getDimensionPixelSize(R.dimen.space);
//        pullLoadMoreRecyclerView.addItemDecoration(new SpaceItemDecoratio(spacingInPixels));


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_yijianfankui;
    }

    @Override
    public void bindEvent() {

    }

    @Override
    public void onRefresh() {
        mPresent.pullRefresh();

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onScrolling(int state) {

    }

    @Override
    public void onHide() {

    }

    @Override
    public void onShow() {

    }


    public void setData(String[][] strArr, int count,int freshType,int currentDataType)
    {

        //设置滑动监听
        pullLoadMoreRecyclerView.setScrollListenerJK(count, Constant.PAGECOUNTNUM);

        if(currentDataType==1)//进入页面返回数据
        {
            if(freshType==0)//下拉刷新
            {
                pullLoadMoreRecyclerView.smoothToTop();

                if(count>0)
                {
                    dataList.clear();
                    for (int i = 0; i < count; i++) {
                        Question objBean = new Question();
                        objBean.setQuestionId(strArr[i][1]);
                        objBean.setTitle(strArr[i][2]);
                        objBean.setContent(strArr[i][3]);
                        objBean.setPublishTime(strArr[i][5]);
                        dataList.add(objBean);
                    }
                }else{
                    dataList.clear();
                    CustomDialog.show(mRootView.getContext(), "无更多数据", false, null, R.layout.text_dialog);
                    CustomDialog.setAutoDismiss(true, 1500);
                }
            }else if(freshType ==1)//上拉加载
            {
                if(count>0)
                {
                    for (int i = 0; i < count; i++) {
                        Question objBean = new Question();
                        objBean.setQuestionId(strArr[i][1]);
                        objBean.setTitle(strArr[i][2]);
                        objBean.setContent(strArr[i][3]);
                        objBean.setPublishTime(strArr[i][5]);
                        dataList.add(objBean);
                    }
                }
            }

            mRecyclerViewAdapter.addAllData(dataList);
        }else if(currentDataType==0)//搜索返回数据
        {
            if(freshType==0)//下拉刷新
            {
                pullLoadMoreRecyclerView.smoothToTop();

                if(count>0)
                {
                    mSearchList.clear();
                    for (int i = 0; i < count; i++) {
                        Question objBean = new Question();
                        objBean.setQuestionId(strArr[i][1]);
                        objBean.setTitle(strArr[i][2]);
                        objBean.setContent(strArr[i][3]);
                        objBean.setPublishTime(strArr[i][5]);
                        mSearchList.add(objBean);
                    }
                }else{
                    mSearchList.clear();
                    CustomDialog.show(mRootView.getContext(), "无更多数据", false, null, R.layout.text_dialog);
                    CustomDialog.setAutoDismiss(true, 1500);
                }
            }else if(freshType ==1)//上拉加载
            {
                if(count>0)
                {
                    for (int i = 0; i < count; i++) {
                        Question objBean = new Question();
                        objBean.setQuestionId(strArr[i][1]);
                        objBean.setTitle(strArr[i][2]);
                        objBean.setContent(strArr[i][3]);
                        objBean.setPublishTime(strArr[i][5]);
                        mSearchList.add(objBean);
                    }
                }
            }
            mRecyclerViewAdapter.addAllData(mSearchList);
        }


    }


}
