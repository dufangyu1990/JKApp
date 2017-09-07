package com.example.jkapp.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

public class SearchEditText extends EditText implements OnFocusChangeListener{

	private static final String TAG = "SearchEditText";
	/**
	* 图标是否默认在左边
	*/
	private boolean isIconLeft = false;

	private Drawable[] drawables; // 控件的图片资源
	private Drawable drawableLeft; // 搜索图标和删除按钮图标

	public SearchEditText(Context context) {
		this(context, null);
		init();
	}

	public SearchEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public SearchEditText(Context context, AttributeSet attrs) {
		this(context, attrs,android.R.attr.editTextStyle);
		init();
	}

	
	private void init() {
		setOnFocusChangeListener(this);
		}

	@Override
	public void onFocusChange(View arg0, boolean hasFocus) {
		if(TextUtils.isEmpty(getText().toString()))
			isIconLeft = hasFocus;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (isIconLeft) { // 如果是默认样式，直接绘制
			this.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
			super.onDraw(canvas);
			} else { // 如果不是默认样式，需要将图标绘制在中间
			if (drawables == null) drawables = getCompoundDrawables();
			if (drawableLeft == null) drawableLeft = drawables[0];
			float textWidth = getPaint().measureText(getHint().toString());
			int drawablePadding = getCompoundDrawablePadding();
			int drawableWidth = drawableLeft.getIntrinsicWidth();
			float bodyWidth = textWidth + drawableWidth + drawablePadding;
			canvas.translate((getWidth() - bodyWidth - getPaddingLeft() - getPaddingRight()) / 2, 0);
			super.onDraw(canvas);
			}
	}
	
	
}
