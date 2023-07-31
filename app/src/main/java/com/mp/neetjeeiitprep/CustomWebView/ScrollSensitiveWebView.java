package com.mp.neetjeeiitprep.CustomWebView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class ScrollSensitiveWebView extends WebView {
    public ScrollSensitiveWebView(Context context) {
        super(context);
    }

    public ScrollSensitiveWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollSensitiveWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
