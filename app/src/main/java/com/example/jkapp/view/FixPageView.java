package com.example.jkapp.view;


import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.example.jkapp.R;
import com.example.jkapp.helper.EventHelper;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dufangyu on 2017/8/31.
 */

public class FixPageView extends ViewImpl{

    private HorizontalStepView stepView;
    private List<StepBean> stepsBeanList = new ArrayList<>();

    private SwipeMenuRecyclerView recyclerView;
    private LinearLayout fix_oneLayout,fix_twoLayout,fix_thressLayout,ll_fixlayout;

    private EditText carownername_editor,carnumber_editor,carVIN_editor,carcolor_editor,fixwhere_editor,departname_editor;
    private TextView next_one_btn,liandongtv,putongtv,pre_two_btn,next_two_btn,pre_button_long,pre_three_btn,submitbtn;
    @Override
    public void initView() {

        fix_oneLayout = findViewById(R.id.fix_onelayout);

        carownername_editor = findViewById(R.id.carownername_editor);
        carnumber_editor = findViewById(R.id.carnumber_editor);
        carVIN_editor = findViewById(R.id.carVIN_editor);
        carcolor_editor = findViewById(R.id.carcolor_editor);
        fixwhere_editor = findViewById(R.id.fixwhere_editor);
        departname_editor = findViewById(R.id.departname_editor);
        next_one_btn = findViewById(R.id.next_one_btn);


        /**
         * fix_two 布局
         * ------------------------------------------------------------------------------------------------
         */
        fix_twoLayout = findViewById(R.id.fix_twolayout);
        stepView = findViewById(R.id.step_view);
        initSteps();
        liandongtv = findViewById(R.id.liandongscantv);
        putongtv = findViewById(R.id.putongscantv);
        recyclerView = findViewById(R.id.liandongDevList);
        ll_fixlayout = findViewById(R.id.ll_fixlayout);
        pre_two_btn = findViewById(R.id.pre_button);
        next_two_btn = findViewById(R.id.next_two_btn);
        pre_button_long = findViewById(R.id.pre_button_long);

        /**
         * fix_three 布局
         * ------------------------------------------------------------------------------------------------------
         */
        fix_thressLayout = findViewById(R.id.fix_threelayout);
        pre_three_btn = findViewById(R.id.pre_three_button);
        submitbtn = findViewById(R.id.submit_three_btn);

    }



    private void initSteps()
    {
        StepBean stepBean0 = new StepBean("填写信息",0);
        StepBean stepBean1 = new StepBean("选择终端",-1);
        StepBean stepBean2 = new StepBean("完成绑定",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);

        stepView
                .setStepViewTexts(stepsBeanList)//总步骤
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(mRootView.getContext(), R.color.darklanse))//设置StepsViewIndicator完成线的颜色
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(mRootView.getContext(), R.color.gray))//设置StepsViewIndicator未完成线的颜色
                .setStepViewComplectedTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.gray))//设置StepsView text完成的颜色
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(mRootView.getContext(), R.color.gray))//设置StepsView text未完成的颜色
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.wancheng))//设置StepsViewIndicator CompleteIcon
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.moren))//设置StepsViewIndicator DefaultIcon
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(mRootView.getContext(), R.drawable.jinxing));//设置StepsViewIndicator AttentionIcon
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fixcar;
    }

    @Override
    public void bindEvent() {

        EventHelper.click(mPresent,next_one_btn,next_two_btn,pre_button_long,pre_two_btn,liandongtv,putongtv);

    }


    public void changeLayoutRes(int resId)
    {

        if(resId== R.id.next_one_btn)
        {
            fix_oneLayout.startAnimation(AnimationUtils.loadAnimation(mRootView.getContext(), R.anim.login_anim_exit));
            fix_oneLayout.setVisibility(View.GONE);
            fix_twoLayout.startAnimation(AnimationUtils.loadAnimation(mRootView.getContext(), R.anim.loginsms_anim_from));
            fix_twoLayout.setVisibility(View.VISIBLE);
            stepsBeanList.get(0).setState(1);
            stepsBeanList.get(1).setState(0);
            stepsBeanList.get(2).setState(-1);
            stepView.reInvalid();


        }else if(resId== R.id.pre_button || resId== R.id.pre_button_long)
        {

            fix_twoLayout.startAnimation(AnimationUtils.loadAnimation(mRootView.getContext(), R.anim.loginsms_anim_exit));
            fix_twoLayout.setVisibility(View.GONE);
            fix_oneLayout.startAnimation(AnimationUtils.loadAnimation(mRootView.getContext(), R.anim.login_anim_from));
            fix_oneLayout.setVisibility(View.VISIBLE);
            stepsBeanList.get(0).setState(0);
            stepsBeanList.get(1).setState(-1);
            stepsBeanList.get(2).setState(-1);
            stepView.reInvalid();

        }else if(resId == R.id.next_two_btn)
        {

            fix_twoLayout.startAnimation(AnimationUtils.loadAnimation(mRootView.getContext(), R.anim.login_anim_exit));
            fix_twoLayout.setVisibility(View.GONE);
            fix_thressLayout.startAnimation(AnimationUtils.loadAnimation(mRootView.getContext(), R.anim.loginsms_anim_from));
            fix_thressLayout.setVisibility(View.VISIBLE);
            stepsBeanList.get(0).setState(1);
            stepsBeanList.get(1).setState(1);
            stepsBeanList.get(2).setState(0);
            stepView.reInvalid();

        }else if(resId==R.id.pre_three_button)
        {
            fix_thressLayout.startAnimation(AnimationUtils.loadAnimation(mRootView.getContext(), R.anim.loginsms_anim_exit));
            fix_thressLayout.setVisibility(View.GONE);
            fix_twoLayout.startAnimation(AnimationUtils.loadAnimation(mRootView.getContext(), R.anim.login_anim_from));
            fix_twoLayout.setVisibility(View.VISIBLE);
            stepsBeanList.get(0).setState(1);
            stepsBeanList.get(1).setState(0);
            stepsBeanList.get(2).setState(-1);
            stepView.reInvalid();
        }
    }


}
