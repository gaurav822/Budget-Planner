package com.gaurav.budgetplanner.Utils.CustomTextFormat.CustomTextFormat;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class NunitoRegularTextView extends TextView {

    public NunitoRegularTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public NunitoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public NunitoRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {

        Typeface customFont=FontCache.getTypeface(FontCache.NUNITO,context);
        setTypeface(customFont);
    }
}