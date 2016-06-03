package com.xzh.objectanimator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;

import com.xzh.objectanimator.apdapter.FragmentAdapter;
import com.xzh.objectanimator.fragment.BaseAnimationFragment;
import com.xzh.objectanimator.fragment.FirstAnimFragment;
import com.xzh.objectanimator.fragment.SecondAnimFragment;
import com.xzh.objectanimator.fragment.ThirdAnimFragment;
import com.xzh.objectanimator.utils.Utils;


public class SplashActivity700 extends FragmentActivity {
	private ViewPager mViewPager;
	private FragmentAdapter mAdapter;
	private int oldPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_yindao);
		initParams();
		initView();

	}

	private void initParams(){
//			Utils.CmPageViews(this, CmViews.SPLASHACTIVITY);
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.view_page);
		mAdapter = new FragmentAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if (oldPosition < 0) {
					oldPosition = 0;
				}
				BaseAnimationFragment baf = null;
				switch (position) {
				case 0:
					reset();
					baf = (FirstAnimFragment) mAdapter.getItem(0);
					baf.playInAnim();
					break;
				case 1:
					reset();
					baf = (SecondAnimFragment) mAdapter.getItem(1);
					baf.playInAnim();
					break;
				case 2:
					reset();
					baf = (ThirdAnimFragment) mAdapter.getItem(2);
					baf.playInAnim();
					break;
				default:
					break;
				}
				oldPosition = position;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void reset() {
		if (oldPosition >= 0) {
			BaseAnimationFragment baf = (BaseAnimationFragment) mAdapter
					.getItem(oldPosition);
			baf.reset();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			((BaseAnimationFragment) mAdapter.getItem(0)).reset();
			((BaseAnimationFragment) mAdapter.getItem(1)).reset();
			((BaseAnimationFragment) mAdapter.getItem(2)).reset();
		}
		return super.onKeyDown(keyCode, event);
	}
}
