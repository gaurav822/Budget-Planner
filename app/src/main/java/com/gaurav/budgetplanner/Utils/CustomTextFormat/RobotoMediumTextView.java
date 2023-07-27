package com.gaurav.budgetplanner.Utils.CustomTextFormat;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.gaurav.budgetplanner.Utils.CustomTextFormat.CustomTextFormat.FontCache;

/**
 * Created by swiftmacbook on 8/7/17.
 */

public class RobotoMediumTextView extends TextView {

    public RobotoMediumTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {

        Typeface customFont= FontCache.getTypeface(FontCache.ROBOTO_MEDIUM,context);
        setTypeface(customFont);
    }
}