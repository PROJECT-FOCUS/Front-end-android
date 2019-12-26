package com.team.focus.ui.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewSFProDisplayUltralightItalic extends TextView {

    public TextViewSFProDisplayUltralightItalic(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewSFProDisplayUltralightItalic(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewSFProDisplayUltralightItalic(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SF-Pro-Display-UltralightItalic.otf");
            setTypeface(tf);
        }
    }
}
