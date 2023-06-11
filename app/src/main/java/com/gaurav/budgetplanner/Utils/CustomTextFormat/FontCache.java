package com.gaurav.budgetplanner.Utils.CustomTextFormat;
import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by swift-android on 5/25/17.
 */
public class FontCache {

    public static final String ROOT = "fonts/",
            FONTAWESOME = ROOT + "fontawesome-webfont.ttf",
            FONTSAJILO = ROOT + "sajilo_font.ttf",
            NUNITO_BLACK = ROOT + "nunito_black.ttf",
            NUNITO_BOLD = ROOT + "volte_bold.ttf",
            NUNITO_SEMI_BOLD = ROOT + "volte_semi_bold.ttf",
            NUNITO = ROOT + "volte_regular.ttf",
            ROBOTO_BOLD = ROOT + "volte_bold.ttf",
            ROBOTO_LIGHT = ROOT + "volte_light.ttf",
            ROBOTO_MEDIUM = ROOT + "volte_medium.ttf",
            ROBOTO_REGULAR = ROOT + "volte_regular.ttf";


    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }
}