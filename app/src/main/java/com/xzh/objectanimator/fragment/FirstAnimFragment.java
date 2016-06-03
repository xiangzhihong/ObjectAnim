package com.xzh.objectanimator.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.xzh.objectanimator.R;

public class FirstAnimFragment extends BaseAnimationFragment {
    private ImageView phoneView, magnifier_view, content_view;
    private FrameLayout content_layout;
    private AnimatorSet mAnimatorSet;
    private ObjectAnimator translateAnimator;
    private AnimationDrawable contentAnim;

    private final static double phoneViewTopMargin = (50 * 1.5) / 800d;
    private final static double magnifierViewTopMargin = (120 * 1.5) / 800d;
    private float startX, endX;
    private Runnable contentRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_page_layout, null);
        initView(view);
//        viewAdaptation();
        if (null != mAnimatorSet) {
            mAnimatorSet.end();
        }
        if (null != contentAnim) {
            contentAnim.stop();
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null == savedInstanceState) {
            playInAnim();
        }
    }

    private void initView(View view) {
        phoneView = (ImageView) view.findViewById(R.id.first_page_phone);
        magnifier_view = (ImageView) view.findViewById(R.id.magnifier_view);
        content_view = (ImageView) view.findViewById(R.id.content_view);
        content_layout = (FrameLayout) view.findViewById(R.id.content_layout);
    }

    private void viewAdaptation() {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) phoneView.getLayoutParams();
        lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        lp.topMargin = (int) (phoneViewTopMargin * height + 0.5);
        lp.width = width * 370 / 480;
        lp.height = height * 526 / 800;
        phoneView.setLayoutParams(lp);

        restoreLayoutParams(0, magnifierViewTopMargin, 322, 331, magnifier_view);
        restoreLayoutParams(0, magnifierViewTopMargin, content_layout);
        restoreLayoutParams(322, 331, content_view);

        lp = (FrameLayout.LayoutParams) magnifier_view.getLayoutParams();
        startX = -lp.width;
        endX = 0;

        if (null != translateAnimator) {
            translateAnimator.setTarget(magnifier_view);
        }
    }

    @Override
    public void playInAnim() {
        isReset = false;
        startAnimation();
        mAnimatorSet.start();
    }

    private void startAnimation() {
        content_layout.setVisibility(View.GONE);
        removeCallback();
        if (null == translateAnimator) {
            translateAnimator = getTranslateAnimator(magnifier_view, translationX, startX, endX, 700);
        }
        if (null == mAnimatorSet) {
            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    content_view.postDelayed(contentRunnable = new Runnable() {
                        @Override
                        public void run() {
                            content_view.removeCallbacks(contentRunnable);
                            if (!isReset) {
                                content_layout.setVisibility(View.VISIBLE);
                                contentAnim = (AnimationDrawable) content_view.getBackground();
                                if (contentAnim.isRunning()) {
                                    contentAnim.stop();
                                }
                                contentAnim.start();
                            }
                        }
                    }, 100);
                }
            });
            mAnimatorSet.play(translateAnimator);
        }
        if (mAnimatorSet.isStarted()) {
            mAnimatorSet.end();
        }
        if (null != contentAnim && contentAnim.isRunning()) {
            contentAnim.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void reset() {
        isReset = true;
        removeCallback();
        if (null != mAnimatorSet) {
            mAnimatorSet.end();
        }
        if (null != contentAnim) {
            contentAnim.stop();
        }
    }

    private void removeCallback() {
        if (contentRunnable != null) {
            content_view.removeCallbacks(contentRunnable);
        }
    }
}