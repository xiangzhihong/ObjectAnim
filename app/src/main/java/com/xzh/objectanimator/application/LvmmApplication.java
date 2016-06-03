package com.xzh.objectanimator.application;

import android.app.Application;

/**
 * Created by xiangzhihong on 2015/11/13 on 11:06.
 */
public class LvmmApplication extends Application {

    private static LvmmApplication instance;

    public static LvmmApplication getInstance() {
        return instance;
    }
}
