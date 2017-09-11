package com.baoyachi.stepview;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepsViewIndicator.OnDrawIndicatorListener;
import com.baoyachi.stepview.bean.StepBean;

import java.util.List;

/**
 * Created by xx on 2017/6/19.
 */

public class HorizontalStepView extends LinearLayout implements OnDrawIndicatorListener {
    private RelativeLayout mTextContainer;
    private HorizontalStepsViewIndicator mStepsViewIndicator;
    private List<StepBean> mStepBeanList;
    private int mComplectingPosition;
    private int mUnComplectedTextColor;
    private int mComplectedTextColor;
    private int mTextSize;
    private TextView mTextView;

    public HorizontalStepView(Context context) {
        this(context, (AttributeSet) null);
    }

    public HorizontalStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mUnComplectedTextColor = ContextCompat.getColor(this.getContext(), R.color.uncompleted_text_color);
        this.mComplectedTextColor = ContextCompat.getColor(this.getContext(), R.color.uncompleted_color);
        this.mTextSize = 14;
        this.init();
    }

    private void init() {
        View rootView = LayoutInflater.from(this.getContext()).inflate(R.layout.widget_horizontal_stepsview, this);
        this.mStepsViewIndicator = (HorizontalStepsViewIndicator) rootView.findViewById(R.id.steps_indicator);
        this.mStepsViewIndicator.setOnDrawListener(this);
        this.mTextContainer = (RelativeLayout) rootView.findViewById(R.id.rl_text_container);
    }

    public HorizontalStepView setStepViewTexts(List<StepBean> stepsBeanList) {
        this.mStepBeanList = stepsBeanList;
        this.mStepsViewIndicator.setStepNum(this.mStepBeanList);
        return this;
    }

    public HorizontalStepView setStepViewUnComplectedTextColor(int unComplectedTextColor) {
        this.mUnComplectedTextColor = unComplectedTextColor;
        return this;
    }

    public HorizontalStepView setStepViewComplectedTextColor(int complectedTextColor) {
        this.mComplectedTextColor = complectedTextColor;
        return this;
    }

    public HorizontalStepView setStepsViewIndicatorUnCompletedLineColor(int unCompletedLineColor) {
        this.mStepsViewIndicator.setUnCompletedLineColor(unCompletedLineColor);
        return this;
    }

    public HorizontalStepView setStepsViewIndicatorCompletedLineColor(int completedLineColor) {
        this.mStepsViewIndicator.setCompletedLineColor(completedLineColor);
        return this;
    }

    public HorizontalStepView setStepsViewIndicatorDefaultIcon(Drawable defaultIcon) {
        this.mStepsViewIndicator.setDefaultIcon(defaultIcon);
        return this;
    }

    public HorizontalStepView setStepsViewIndicatorCompleteIcon(Drawable completeIcon) {
        this.mStepsViewIndicator.setCompleteIcon(completeIcon);
        return this;
    }

    public HorizontalStepView setStepsViewIndicatorAttentionIcon(Drawable attentionIcon) {
        this.mStepsViewIndicator.setAttentionIcon(attentionIcon);
        return this;
    }

    public HorizontalStepView setTextSize(int textSize) {
        if (textSize > 0) {
            this.mTextSize = textSize;
        }

        return this;
    }

    public void ondrawIndicator() {
        int mStepNum = this.mStepBeanList.size();
        if (this.mStepBeanList != null && this.mStepBeanList.size() > 0) {
            for (int i = 0; i < mStepNum; ++i) {
                StepBean stepsBean = (StepBean) this.mStepBeanList.get(i);
                if (stepsBean.getState() == 0) {
                    this.mComplectingPosition = i;
                }
            }
        }
        if (this.mTextContainer != null) {
            this.mTextContainer.removeAllViews();
            List complectedXPosition = this.mStepsViewIndicator.getCircleCenterPointPositionList();
            if (this.mStepBeanList != null && complectedXPosition != null && complectedXPosition.size() > 0) {
                for (int i = 0; i < this.mStepBeanList.size(); ++i) {
                    this.mTextView = new TextView(this.getContext());
                    this.mTextView.setTextSize(2, (float) this.mTextSize);
                    this.mTextView.setText(((StepBean) this.mStepBeanList.get(i)).getName());
                    int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                    this.mTextView.measure(spec, spec);
                    int measuredWidth = this.mTextView.getMeasuredWidth();
                    this.mTextView.setX(((Float) complectedXPosition.get(i)).floatValue() - (float) (measuredWidth / 2));
                    this.mTextView.setLayoutParams(new LayoutParams(-2, -2));
                    if (i <= this.mComplectingPosition) {
                        this.mTextView.setTypeface((Typeface) null, 1);
                        this.mTextView.setTextColor(this.mComplectedTextColor);
                    } else {
                        this.mTextView.setTextColor(this.mUnComplectedTextColor);
                    }
                    this.mTextContainer.addView(this.mTextView);
                }
            }
        }

    }


    //重新刷新
    public void reInvalid()
    {
        mStepsViewIndicator.invalidate();
    }

}
