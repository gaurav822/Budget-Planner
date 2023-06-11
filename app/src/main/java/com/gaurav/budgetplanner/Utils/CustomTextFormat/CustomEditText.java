package com.gaurav.budgetplanner.Utils.CustomTextFormat;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;



public class CustomEditText extends EditText{

    public CustomEditText(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
//        setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {

        Typeface customFont = FontCache.getTypeface(FontCache.ROBOTO_MEDIUM, context);
        setTypeface(customFont);
        setTextSize(16);
    }
}
