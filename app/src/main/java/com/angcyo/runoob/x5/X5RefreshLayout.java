package com.angcyo.runoob.x5;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.angcyo.uiview.rsen.EmptyRefreshLayout;

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2017/04/20 10:55
 * 修改人员：Robi
 * 修改时间：2017/04/20 10:55
 * 修改备注：
 * Version: 1.0.0
 */
public class X5RefreshLayout extends EmptyRefreshLayout {

    private boolean handler;

    public X5RefreshLayout(Context context) {
        super(context);
    }

    public X5RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean innerCanChildScrollVertically(View view, int direction, float rawX, float rawY) {
        if (view instanceof X5WebView) {
            if (direction > 0) {
                //手指向上滑动
                return true;
            } else {
                return ((X5WebView) view).canTopScroll();
            }
        } else {
            return super.innerCanChildScrollVertically(view, direction, rawX, rawY);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        handlerEvent(event);

        if (handler || isCancel(MotionEventCompat.getActionMasked(event))) {
            return super.onInterceptTouchEvent(event);
        } else {
            return false;
        }
    }

    protected void handlerEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);//event.getActionMasked();

        if (action == MotionEvent.ACTION_DOWN) {
            if (mTargetView instanceof X5WebView) {
                handler = ((X5WebView) mTargetView).isStopInTop();
            } else {
                handler = true;
            }
        } else if (isCancel(action)) {
            handler = false;
        }
    }

    private boolean isCancel(int action) {
        return action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handlerEvent(event);

        if (handler || isCancel(MotionEventCompat.getActionMasked(event))) {
            return super.onTouchEvent(event);
        } else {
            return false;
        }
    }
}
