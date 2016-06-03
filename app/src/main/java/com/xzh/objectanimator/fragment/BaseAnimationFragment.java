package com.xzh.objectanimator.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.xzh.objectanimator.MainActivity;
import com.xzh.objectanimator.application.LvmmApplication;
import com.xzh.objectanimator.utils.Utils;


public abstract class BaseAnimationFragment extends Fragment {

	protected LvmmApplication app;
	protected int width, height;
	protected float mAnimStartX = -1, mAnimStartY = -1;
	// 是否被重置。当动画切换或者离开动画类的时候要重置。
	protected boolean isReset;

	protected final String alpha = "alpha", translationX = "translationX", translationY = "translationY",
			scaleX = "scaleX", scaleY = "scaleY", x = "x", y = "y";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = LvmmApplication.getInstance();
		DisplayMetrics dm = Utils.getDisplayMetrics(getActivity());
		width = dm.widthPixels;
		height = dm.heightPixels;
	}

	/**
	 * 获取当前view的位置
	 * @param view
	 * @param viewMargin
	 * @param isLeftLocation
	 * @return
	 */
	protected float getLocation(View view, double viewMargin, boolean isLeftLocation) {
		float location = 0f;
		if (isLeftLocation) {
			location = ViewHelper.getX(view);
		} else {
			location = ViewHelper.getY(view);
		}
		// 遇到过会出现ViewHelper.getX=0或者ViewHelper.getY=0的情况(小概率)
		if (location == 0f) {
			if (isLeftLocation) {
				location = (float) (width * viewMargin);
			} else {
				location = (float) (height * viewMargin);
			}
		}
		return location;
	}

	protected void restoreLayoutParams(double leftMargin, double topMargin, int w, int h, View view) {
		FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
		lp.gravity = Gravity.LEFT | Gravity.TOP;
		if (0d != leftMargin) {
			lp.leftMargin = (int) (leftMargin * width + 0.5);
		}
		if (0d != topMargin) {
			lp.topMargin = (int) (topMargin * height + 0.5);
		}
		if (0 != w) {
			lp.width = width * w / 480;
		}
		if (0 != h) {
			lp.height = height * h / 800;
		}
		view.setLayoutParams(lp);
	}

	protected void restoreLayoutParams(double leftMargin, double topMargin, View view) {
		restoreLayoutParams(leftMargin, topMargin, 0, 0, view);
	}
	
	protected void restoreLayoutParams(int w, int h, View view) {
		restoreLayoutParams(0, 0, w, h, view);
	}

	/**
	 * 执行动画
	 */
	public abstract void playInAnim();

	public abstract void reset();
	
	/**
	 * 
	* @Title: defaultAnim 
	* @Description: 默认的空动画，一般用来做其他动画延迟时用到，源码里面也是这般用法。
	* @param @param delay
	* @return ValueAnimator    返回类型 
	 */
	protected ValueAnimator defaultAnim(long delay) {
		ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
		anim.setDuration(delay);
		return anim;
	}

	protected ObjectAnimator getAlphaAnimator(View view, float fromAlpha, float toAlpha, long duration){
		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, alpha, fromAlpha, toAlpha);
		if(-1 != duration){
			alphaAnimator.setDuration(duration);
		}
		return alphaAnimator;
	}

	protected ObjectAnimator getScaleAnimator(View view, String propertyName, float fromScale, float toScale, long duration){
		ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(view, propertyName, fromScale, toScale);
		if(-1 != duration){
			scaleAnimator.setDuration(duration);
		}
		return scaleAnimator;
	}

	protected ObjectAnimator getTranslateAnimator(View view, String propertyName, float start, float end, long duration){
		ObjectAnimator translateAnimator = ObjectAnimator.ofFloat(view, propertyName, start, end);
		if(-1 != duration){
			translateAnimator.setDuration(duration);
		}
		return translateAnimator;
	}
	
	protected void startNextActivity() {
		isReset = true;
			Intent intent = new Intent(getActivity(), MainActivity.class);
			startActivity(intent);
			getActivity().finish();
	}
}
