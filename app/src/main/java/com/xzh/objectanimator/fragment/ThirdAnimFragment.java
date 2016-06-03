package com.xzh.objectanimator.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xzh.objectanimator.R;

/**
 * Created by huchangfu on 2015/9/9.
 */
public class ThirdAnimFragment extends BaseAnimationFragment {
    private FrameLayout routeALayout, routeBLayout, routeCLayout;
    private ImageView routeAView, routeBView, routeCView, phoneView, enterView;

    private final static double routeALeftMargin = (10 * 1.5) / 480d, routeATopMargin = (70 * 1.5) / 800d;
    private final static double routeBLeftMargin = (105 * 1.5) / 480d, routeBTopMargin = (20 * 1.5) / 800d;
    private final static double routeCLeftMargin = (215 * 1.5) / 480d, routeCTopMargin = (70 * 1.5) / 800d;
    private final static double phoneLeftMargin = (45 * 1.5) / 480d;
    private final static double enterViewBottomMargin = (30 * 1.5) / 800d;

    private AnimationDrawable routeAAnim, routeBAnim, routeCAnim;
    private Runnable routeARunnable, routeBRunnable, routeCRunnable, phoneRunnable, cycleRunnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_page_layout, null);
        initView(view);
//        viewAdaptation();
        return view;
    }

    private void initView(View view) {
        routeALayout = (FrameLayout) view.findViewById(R.id.route_a_layout);
        routeBLayout = (FrameLayout) view.findViewById(R.id.route_b_layout);
        routeCLayout = (FrameLayout) view.findViewById(R.id.route_c_layout);
        routeAView = (ImageView) view.findViewById(R.id.route_a_view);
        routeBView = (ImageView) view.findViewById(R.id.route_b_view);
        routeCView = (ImageView) view.findViewById(R.id.route_c_view);
        phoneView = (ImageView) view.findViewById(R.id.phone_view);
        enterView = (ImageView) view.findViewById(R.id.enter_view);
        enterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNextActivity();
            }
        });
    }

    private void viewAdaptation() {
        restoreLayoutParams(routeALeftMargin, routeATopMargin, routeALayout);
        restoreLayoutParams(routeBLeftMargin, routeBTopMargin, routeBLayout);
        restoreLayoutParams(routeCLeftMargin, routeCTopMargin, routeCLayout);
        restoreLayoutParams(146, 147, routeAView);
        restoreLayoutParams(158, 169, routeBView);
        restoreLayoutParams(146, 147, routeCView);

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) phoneView.getLayoutParams();
        lp.gravity = Gravity.CENTER_VERTICAL;
        lp.leftMargin = (int) (phoneLeftMargin * width + 0.5);
        phoneView.setLayoutParams(lp);

        lp = (FrameLayout.LayoutParams) enterView.getLayoutParams();
        lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        lp.bottomMargin = (int) (enterViewBottomMargin * height + 0.5);
        enterView.setLayoutParams(lp);
    }

    @Override
    public void playInAnim() {
        isReset = false;
        startAnimation();
    }

    private void removeCallbacks() {
        if (routeARunnable != null) {
            routeAView.removeCallbacks(routeARunnable);
        }
        if (routeBRunnable != null) {
            routeBView.removeCallbacks(routeBRunnable);
        }
        if (routeCRunnable != null) {
            routeCView.removeCallbacks(routeCRunnable);
        }
        if (phoneRunnable != null) {
            phoneView.removeCallbacks(phoneRunnable);
        }
        if (cycleRunnable != null) {
            routeCLayout.removeCallbacks(cycleRunnable);
        }
    }

    private void startAnimation() {
        removeCallbacks();

        if (isReset) return;
        routeALayout.setVisibility(View.GONE);
        routeBLayout.setVisibility(View.GONE);
        routeCLayout.setVisibility(View.GONE);
        routeAView.setBackgroundResource(R.drawable.route_a_anim);
        routeCView.setBackgroundResource(R.drawable.route_c_anim);
        phoneView.setImageResource(R.drawable.third_page_man1);

        // 线路A
        routeAAnim = (AnimationDrawable) routeAView.getBackground();
        if (routeAAnim.isRunning()) {
            routeAAnim.stop();
        }
        routeAView.postDelayed(routeARunnable = new Runnable() {
            @Override
            public void run() {
                routeAView.removeCallbacks(routeARunnable);
                if (isReset) return;
                routeALayout.setVisibility(View.VISIBLE);
                routeAAnim.start();
            }
        }, 100);

        // 线路B
        routeBAnim = (AnimationDrawable) routeBView.getBackground();
        if (routeBAnim.isRunning()) {
            routeBAnim.stop();
        }
        routeBView.postDelayed(routeBRunnable = new Runnable() {
            @Override
            public void run() {
                routeBView.removeCallbacks(routeBRunnable);
                if (isReset) return;
                routeBLayout.setVisibility(View.VISIBLE);
                routeBAnim.start();

                phoneView.postDelayed(phoneRunnable = new Runnable() {
                    @Override
                    public void run() {
                        phoneView.removeCallbacks(phoneRunnable);
                        if (isReset) return;
                        phoneView.setImageResource(R.drawable.third_page_man2);

                        routeAAnim.stop();
                        routeAView.setBackgroundResource(R.drawable.route_a3_translucent);
                        routeCAnim.stop();
                        routeCView.setBackgroundResource(R.drawable.route_c3_translucent);
                    }
                }, 1000);
            }
        }, 400);

        // 线路C
        routeCAnim = (AnimationDrawable) routeCView.getBackground();
        if (routeCAnim.isRunning()) {
            routeCAnim.stop();
        }
        routeCView.postDelayed(routeCRunnable = new Runnable() {
            @Override
            public void run() {
                if (isReset) return;
                routeCView.removeCallbacks(routeCRunnable);
                routeCLayout.setVisibility(View.VISIBLE);
                routeCAnim.start();

                routeCLayout.postDelayed(cycleRunnable = new Runnable() {
                    @Override
                    public void run() {
                        routeCLayout.removeCallbacks(cycleRunnable);
                        if (!isReset) {
                            startAnimation();
                        }
                    }
                }, 2300);
            }
        }, 700);
    }

    @Override
    public void reset() {
        isReset = true;
        removeCallbacks();
        if (null != routeAAnim) {
            routeAAnim.stop();
        }
        if (null != routeBAnim) {
            routeBAnim.stop();
        }
        if (null != routeCAnim) {
            routeCAnim.stop();
        }
    }
}