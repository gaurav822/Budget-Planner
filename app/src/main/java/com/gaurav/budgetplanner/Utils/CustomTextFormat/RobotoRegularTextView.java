package com.gaurav.budgetplanner.Utils.CustomTextFormat;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.gaurav.budgetplanner.Utils.CustomTextFormat.CustomTextFormat.FontCache;

/**
 * Created by swiftmacbook on 8/7/17.
 */

public class RobotoRegularTextView extends androidx.appcompat.widget.AppCompatTextView {

    public RobotoRegularTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public RobotoRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {

        Typeface customFont = FontCache.getTypeface(FontCache.ROBOTO_REGULAR, context);
        setTypeface(customFont);
    }
}