package com.example.huozhenpeng.interviewui.barlink;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.example.huozhenpeng.interviewui.R;

/**
 * eurostile字体的textview
 * @author huozhenpeng
 *
 */
public class TextView extends android.support.v7.widget.AppCompatTextView{

	private boolean special;
	private Context context;
	private void init() {
		if(special)//true代表特殊字体
		{
			if(context==null)
				return;
			String fontPath = "eurostile.ttf";
			Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
			this.setTypeface(tf);
		}
	}

	public boolean isSpecial() {
		return special;
	}

	public void setSpecial(boolean special) {
		this.special = special;
		init();
	}

	public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context=context;
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
				attrs, R.styleable.textview, 0, 0);
		int n = typedArray.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = typedArray.getIndex(i);
			switch (attr) {
				case R.styleable.textview_typeface:
					special = typedArray.getBoolean(i, false);
					break;
				default:
					break;
			}
		}
		typedArray.recycle();
		init();
	}

	public TextView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public TextView(Context context) {
		this(context,null);
	}


}
