package com.baoyachi.stepview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.baoyachi.stepview.bean.StepBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xx on 2017/6/19.
 */

public class HorizontalStepsViewIndicator extends View {
    private int defaultStepIndicatorNum;
    private float mCompletedLineHeight;
    private float mCircleRadius;
    private Drawable mCompleteIcon;
    private Drawable mAttentionIcon;
    private Drawable mDefaultIcon;
    private float mCenterY;
    private float mLeftY;
    private float mRightY;
    private List<StepBean> mStepBeanList;
    private int mStepNum;
    private float mLinePadding;
    private List<Float> mCircleCenterPointPositionList;
    private Paint mUnCompletedPaint;
    private Paint mCompletedPaint;
    private int mUnCompletedLineColor;
    private int mCompletedLineColor;
    private PathEffect mEffects;
    private int mComplectingPosition;
    private Path mPath;
    private OnDrawIndicatorListener mOnDrawListener;
    private int screenWidth;

    public void setOnDrawListener(OnDrawIndicatorListener onDrawListener) {
        this.mOnDrawListener = onDrawListener;
    }

    public float getCircleRadius() {
        return this.mCircleRadius;
    }

    public HorizontalStepsViewIndicator(Context context) {
        this(context, (AttributeSet) null);
    }

    public HorizontalStepsViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalStepsViewIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.defaultStepIndicatorNum = (int) TypedValue.applyDimension(1, 40.0F, this.getResources().getDisplayMetrics());
        this.mStepNum = 0;
        this.mUnCompletedLineColor = ContextCompat.getColor(this.getContext(), R.color.uncompleted_color);
        this.mCompletedLineColor = -1;
        this.init();
    }

    private void init() {
        this.mStepBeanList = new ArrayList();
        this.mPath = new Path();
        this.mEffects = new DashPathEffect(new float[]{8.0F, 8.0F, 8.0F, 8.0F}, 1.0F);
        this.mCircleCenterPointPositionList = new ArrayList();
        this.mUnCompletedPaint = new Paint();
        this.mCompletedPaint = new Paint();
        this.mUnCompletedPaint.setAntiAlias(true);
        this.mUnCompletedPaint.setColor(this.mUnCompletedLineColor);
        this.mUnCompletedPaint.setStyle(Paint.Style.STROKE);
        this.mUnCompletedPaint.setStrokeWidth(2.0F);


        this.mCompletedPaint.setAntiAlias(true);
        this.mCompletedPaint.setColor(this.mCompletedLineColor);
        this.mCompletedPaint.setStyle(Paint.Style.STROKE);
        this.mCompletedPaint.setStrokeWidth(2.0F);

//        this.mCompletedPaint.setPathEffect(this.mEffects);
        this.mCompletedPaint.setStyle(Paint.Style.FILL);

        this.mUnCompletedPaint.setPathEffect(this.mEffects);

        this.mCompletedLineHeight = 0.05F * (float) this.defaultStepIndicatorNum;
        this.mCircleRadius = 0.28F * (float) this.defaultStepIndicatorNum;
//        this.mLinePadding = 0.85F * (float) this.defaultStepIndicatorNum;
        this.mLinePadding = 2.5f * (float) this.defaultStepIndicatorNum;
        this.mCompleteIcon = ContextCompat.getDrawable(this.getContext(), R.drawable.complted);
        this.mAttentionIcon = ContextCompat.getDrawable(this.getContext(), R.drawable.attention);
        this.mDefaultIcon = ContextCompat.getDrawable(this.getContext(), R.drawable.default_icon);
    }

    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = this.defaultStepIndicatorNum * 2;
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec)) {
            this.screenWidth = MeasureSpec.getSize(widthMeasureSpec);
        }

        int height = this.defaultStepIndicatorNum;
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec)) {
            height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
        }

        width = (int) ((float) this.mStepNum * this.mCircleRadius * 2.0F - (float) (this.mStepNum - 1) * this.mLinePadding);
        this.setMeasuredDimension(width, height);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mCenterY = 0.5F * (float) this.getHeight();
        this.mLeftY = this.mCenterY - this.mCompletedLineHeight / 2.0F;
        this.mRightY = this.mCenterY + this.mCompletedLineHeight / 2.0F;
        this.mCircleCenterPointPositionList.clear();

        for (int i = 0; i < this.mStepNum; ++i) {
            float paddingLeft = ((float) this.screenWidth - (float) this.mStepNum * this.mCircleRadius * 2.0F - (float) (this.mStepNum - 1) * this.mLinePadding) / 2.0F;
            this.mCircleCenterPointPositionList.add(Float.valueOf(paddingLeft + this.mCircleRadius + (float) i * this.mCircleRadius * 2.0F + (float) i * this.mLinePadding));
        }

        if (this.mOnDrawListener != null) {
            this.mOnDrawListener.ondrawIndicator();
        }

    }

    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mOnDrawListener != null) {
            this.mOnDrawListener.ondrawIndicator();
        }

        this.mUnCompletedPaint.setColor(this.mUnCompletedLineColor);
        this.mCompletedPaint.setColor(this.mCompletedLineColor);

        int i;
        float currentComplectedXPosition;
        for (i = 0; i < this.mCircleCenterPointPositionList.size() - 1; ++i) {
            currentComplectedXPosition = ((Float) this.mCircleCenterPointPositionList.get(i)).floatValue();
            float rect = ((Float) this.mCircleCenterPointPositionList.get(i + 1)).floatValue();
            if (i <= this.mComplectingPosition && ((StepBean) this.mStepBeanList.get(0)).getState() != -1 && ((StepBean) this.mStepBeanList.get(0)).getState() != 0) {
                canvas.drawRect(currentComplectedXPosition + this.mCircleRadius - 10.0F, this.mLeftY, rect - this.mCircleRadius + 10.0F, this.mRightY, this.mCompletedPaint);


//                mPath.reset();
//                mPath.moveTo(currentComplectedXPosition + mCircleRadius - 10, mLeftY);
//                mPath.lineTo(rect - mCircleRadius + 10, mRightY);
//                canvas.drawPath(mPath, mCompletedPaint);



            } else {
                this.mPath.moveTo(currentComplectedXPosition + this.mCircleRadius, this.mCenterY);
                this.mPath.lineTo(rect - this.mCircleRadius, this.mCenterY);
                canvas.drawPath(this.mPath, this.mUnCompletedPaint);
            }
        }

        for (i = 0; i < this.mCircleCenterPointPositionList.size(); ++i) {
            currentComplectedXPosition = ((Float) this.mCircleCenterPointPositionList.get(i)).floatValue();
            Rect var6 = new Rect((int) (currentComplectedXPosition - this.mCircleRadius), (int) (this.mCenterY - this.mCircleRadius), (int) (currentComplectedXPosition + this.mCircleRadius), (int) (this.mCenterY + this.mCircleRadius));
            StepBean stepsBean = (StepBean) this.mStepBeanList.get(i);
            if (stepsBean.getState() == -1) {
                this.mDefaultIcon.setBounds(var6);
                this.mDefaultIcon.draw(canvas);
            } else if (stepsBean.getState() == 0) {
                this.mCompletedPaint.setColor(-1);
                canvas.drawCircle(currentComplectedXPosition, this.mCenterY, this.mCircleRadius * 1.1F, this.mCompletedPaint);
                this.mAttentionIcon.setBounds(var6);
                this.mAttentionIcon.draw(canvas);
            } else if (stepsBean.getState() == 1) {
                this.mCompleteIcon.setBounds(var6);
                this.mCompleteIcon.draw(canvas);
            }
        }

    }

    public List<Float> getCircleCenterPointPositionList() {
        return this.mCircleCenterPointPositionList;
    }

    public void setStepNum(List<StepBean> stepsBeanList) {
        this.mStepBeanList = stepsBeanList;
        this.mStepNum = this.mStepBeanList.size();
        if (this.mStepBeanList != null && this.mStepBeanList.size() > 0) {
            for (int i = 0; i < this.mStepNum; ++i) {
                StepBean stepsBean = (StepBean) this.mStepBeanList.get(i);
                if (stepsBean.getState() == 1) {
                    this.mComplectingPosition = i;
                }
            }
        }

        this.requestLayout();
    }

    public void setUnCompletedLineColor(int unCompletedLineColor) {
        this.mUnCompletedLineColor = unCompletedLineColor;
    }

    public void setCompletedLineColor(int completedLineColor) {
        this.mCompletedLineColor = completedLineColor;
    }

    public void setDefaultIcon(Drawable defaultIcon) {
        this.mDefaultIcon = defaultIcon;
    }

    public void setCompleteIcon(Drawable completeIcon) {
        this.mCompleteIcon = completeIcon;
    }

    public void setAttentionIcon(Drawable attentionIcon) {
        this.mAttentionIcon = attentionIcon;
    }

    public interface OnDrawIndicatorListener {
        void ondrawIndicator();
    }
}
