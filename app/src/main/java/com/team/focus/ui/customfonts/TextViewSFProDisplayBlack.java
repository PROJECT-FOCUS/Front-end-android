package com.team.focus.ui.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewSFProDisplayBlack extends TextView {

    public TextViewSFProDisplayBlack(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewSFProDisplayBlack(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewSFProDisplayBlack(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SF-Pro-Display-Black.otf");
            setTypeface(tf);
        }
    }
}
