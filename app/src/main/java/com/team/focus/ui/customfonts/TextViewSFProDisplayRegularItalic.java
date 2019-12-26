package com.team.focus.ui.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

class TextViewSFProDisplayRegularItalicextends extends TextView {

    public TextViewSFProDisplayRegularItalicextends(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewSFProDisplayRegularItalicextends(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewSFProDisplayRegularItalicextends(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SF-Pro-Display-RegularItalic.otf");
            setTypeface(tf);
        }
    }
}
