package com.example.jkapp.view;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.jkapp.R;
import com.example.jkapp.adapter.RecycleViewAdapter;
import com.example.jkapp.bean.Question;
import com.example.jkapp.customview.CustomDialog;
import com.example.jkapp.customview.SearchEditText;
import com.example.jkapp.helper.EventHelper;
import com.example.jkapp.utils.Constant;
import com.example.jkapp.utils.LogUtil;
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
    private SearchEditText searchEditText;
    private InputMethodManager inputmanger;
    private Handler myHandler = new Handler();

    private TextView titletext,backtext;

    private int tempCount;//临时记录上次加载的数据条数，恢复数据的时候用来判断是否能够上拉加载


    @Override
    public void initView() {

        titletext = findViewById(R.id.title_text);
        backtext = findViewById(R.id.back_img);
        backtext.setVisibility(View.VISIBLE);
        titletext.setText(mRootView.getContext().getString(R.string.returnAdvs));
        backtext.setText(mRootView.getContext().getString(R.string.myself));



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
        searchEditText = findViewById(R.id.searchedittext);
        inputmanger = (InputMethodManager) mRootView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //监听键盘enter键事件
        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //当actionId == XX_SEND 或者 XX_DONE时都触发
                //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
                //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    inputmanger.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    mPresent.presentCallBack("","",searchEditText.getText().toString().trim());
                }
                return false;
            }
        });



        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (arg0.length() == 0) {

                    mPresent.presentCallBack("","","");
                }
            }

        });


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_yijianfankui;
    }

    @Override
    public void bindEvent() {

        EventHelper.click(mPresent,backtext);
    }

    @Override
    public void onRefresh() {

        pullLoadMoreRecyclerView.setPushRefreshEnable(true);
        mPresent.pullRefresh();

    }

    @Override
    public void onLoadMore() {
            mPresent.pushLoadMore();

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


    public void restoreOldData(int currentDataType)
    {
        LogUtil.d("dfy","restoreOldData  dataList.size = "+dataList.size());
        LogUtil.d("dfy","currentDataType = "+currentDataType);
        pullLoadMoreRecyclerView.setPushRefreshEnable(true);
        mRecyclerViewAdapter.addAllData(dataList);
        pullLoadMoreRecyclerView.setScrollListenerJK(tempCount, Constant.PAGECOUNTNUM);
    }


    public void setData(String[][] strArr, int count,int freshType,int currentDataType)
    {

        LogUtil.d("dfy","count = "+count);

        pullLoadMoreRecyclerView.setPushRefreshEnable(true);
        //设置滑动监听
        pullLoadMoreRecyclerView.setScrollListenerJK(count, Constant.PAGECOUNTNUM);

        if(currentDataType==1)//进入页面返回数据
        {


            tempCount = count;
            if(freshType==0)//下拉刷新
            {
//                pullLoadMoreRecyclerView.smoothToTop();

                if(count>0)
                {
                    LogUtil.d("dfy","下拉刷新");
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
//                pullLoadMoreRecyclerView.smoothToTop();

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
        cancleAnim();
    }
    public void cancleAnim()
    {

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();

            }
        },1000);

    }

}
