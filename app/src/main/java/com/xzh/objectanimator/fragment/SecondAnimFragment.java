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

public class SecondAnimFragment extends BaseAnimationFragment {
    private FrameLayout goldLayout, manLayout, boxLayout;
    private ImageView quan1View, quan2View, goldView, manView, boxView;
    private AnimatorSet mAnimatorSet;
    private ObjectAnimator translateAnimator;
    private AnimationDrawable goldAnim, manAnim, boxAnim;

    private final static double quan1LeftMargin = (180 * 1.5) / 480d, quan1TopMargin = (310 * 1.5) / 800d;
    private final static double quan2LeftMargin = (230 * 1.5) / 480d, quan2TopMargin = (305 * 1.5) / 800d;
    private final static double goldLeftMargin = (205 * 1.5) / 480d, goldTopMargin = (320 * 1.5) / 800d;
    private final static double manTopMargin = (75 * 1.5) / 800d;
    private final static double boxLeftMargin = (40 * 1.5) / 480d, boxTopMargin = (255 * 1.5) / 800d;

    private float startX, endX;
    private Runnable goldRunnable, manRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_page_layout, null);
        initView(view);
//        viewAdaptation();
        return view;
    }

    private void initView(View view) {
        goldLayout = (FrameLayout) view.findViewById(R.id.gold_layout);
        manLayout = (FrameLayout) view.findViewById(R.id.man_layout);
        boxLayout = (FrameLayout) view.findViewById(R.id.box_layout);
        quan1View = (ImageView) view.findViewById(R.id.quan1_view);
        quan2View = (ImageView) view.findViewById(R.id.quan2_view);
        goldView = (ImageView) view.findViewById(R.id.gold_view);
        manView = (ImageView) view.findViewById(R.id.man_view);
        boxView = (ImageView) view.findViewById(R.id.box_view);
    }

    private void viewAdaptation() {
        restoreLayoutParams(quan1LeftMargin, quan1TopMargin, 58, 22, quan1View);
        restoreLayoutParams(quan2LeftMargin, quan2TopMargin, 58, 22, quan2View);
        restoreLayoutParams(goldLeftMargin, goldTopMargin, goldLayout);
        restoreLayoutParams(boxLeftMargin, boxTopMargin, boxLayout);
        restoreLayoutParams(77, 38, goldView);
        restoreLayoutParams(480, 407, manView);
        restoreLayoutParams(162, 162, boxView);

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) manLayout.getLayoutParams();
        lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        lp.topMargin = (int) (manTopMargin * height + 0.5);
        manLayout.setLayoutParams(lp);

        startX = width;
        endX = 0;
    }

    @Override
    public void playInAnim() {
        isReset = false;
        startAnimation();
        mAnimatorSet.start();
    }

    private void startAnimation() {
        quan1View.setVisibility(View.GONE);
        quan2View.setVisibility(View.GONE);
        goldLayout.setVisibility(View.GONE);
        removeCallback();
        if (null != manAnim) {
            manAnim.stop();
            manAnim = null;
        }
        if (null == translateAnimator) {
            translateAnimator = getTranslateAnimator(manLayout, translationX, startX, endX, 700);
            translateAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    manLayout.setVisibility(View.VISIBLE);
                    manView.setBackgroundResource(R.drawable.man1);
                    if (null == goldAnim) {
                        goldAnim = (AnimationDrawable) goldView.getBackground();
                    }
                    if (goldAnim.isRunning()) {
                        goldAnim.stop();
                    }
                    goldView.postDelayed(goldRunnable = new Runnable() {
                        @Override
                        public void run() {
                            goldView.removeCallbacks(goldRunnable);
                            if (!isReset) {
                                goldLayout.setVisibility(View.VISIBLE);
                                goldAnim.start();
                                quan1View.setVisibility(View.VISIBLE);
                                quan2View.setVisibility(View.VISIBLE);
                            }
                        }
                    }, 500);
                }
            });
        }
        if (null == mAnimatorSet) {
            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    if (!isReset) {
                        super.onAnimationStart(animation);
                        manLayout.setVisibility(View.GONE);
                        quan1View.setVisibility(View.GONE);
                        quan2View.setVisibility(View.GONE);
                        goldLayout.setVisibility(View.GONE);
                        if (null == boxAnim) {
                            boxAnim = (AnimationDrawable) boxView.getBackground();
                        }
                        if (boxAnim.isRunning()) {
                            boxAnim.stop();
                        }
                        boxAnim.start();
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (!isReset) {
                        super.onAnimationEnd(animation);
						mAnimatorSet.start();
                        if (null != manAnim) {
                            manAnim.stop();
                            manAnim = null;
                        }
                        manView.setBackgroundResource(R.drawable.man_anim);
                        manAnim = (AnimationDrawable) manView.getBackground();
                        manAnim.start();
                        manView.postDelayed(manRunnable = new Runnable() {
                            @Override
                            public void run() {
                                manView.removeCallbacks(manRunnable);
                                if (!isReset) {
                                    mAnimatorSet.start();
                                }
                            }
                        }, 1300);
                    }
                }
            });
            mAnimatorSet.play(defaultAnim(500)).before(translateAnimator);
        }
    }

    @Override
    public void reset() {
        isReset = true;
        removeCallback();
        if (null != mAnimatorSet) {
            mAnimatorSet.end();
        }
        if (null != boxAnim) {
            boxAnim.stop();
        }
        if (null != manAnim) {
            manAnim.stop();
        }
        if (null != goldAnim) {
            goldAnim.stop();
        }
    }

    private void removeCallback() {
        if (goldRunnable != null) {
            goldView.removeCallbacks(goldRunnable);
        }
        if (manRunnable != null) {
            manView.removeCallbacks(manRunnable);
        }
    }
}