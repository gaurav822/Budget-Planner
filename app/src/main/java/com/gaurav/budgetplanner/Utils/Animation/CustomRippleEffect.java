package com.gaurav.budgetplanner.Utils.Animation;

import static android.graphics.Path.Direction.CW;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import androidx.core.content.ContextCompat;

import com.gaurav.budgetplanner.R;

public class CustomRippleEffect extends Drawable implements ValueAnimator.AnimatorUpdateListener {


    private float pixelToDen;

    private Path mPath;

    private Paint mPaint;

    private int[] circlePoint;

    private Context context;

    private ValueAnimator valueAnimator;

    private int CIRCLE_RADIUS = 45;

    private static Interpolator interpolator = new DecelerateInterpolator();

    private float fadeOutAlpha;


    public CustomRippleEffect(Context context) {
        this.context = context;
        init();
    }

    public void setCircleRadius(int circleRadius) {
        CIRCLE_RADIUS = circleRadius;
    }

    private void init() {
        pixelToDen = context.getResources().getDisplayMetrics().density;

        mPath = new Path();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(pixelToDen * 1);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(context, R.color.colorRed));

        circlePoint = new int[2];

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void createRipple(View view) {
        view.getLocationOnScreen(circlePoint);

        circlePoint[0] = (int) (view.getX() + view.getWidth() / 2);
        circlePoint[1] = (int) (view.getY() + view.getHeight() / 2);

        startRippleEffect();

    }

    public void createRippleFromXY(float x, float y) {

        circlePoint[0] = (int) x;
        circlePoint[1] = (int) y;

        startRippleEffect();
    }

    private void startRippleEffect() {
        if (valueAnimator != null && valueAnimator.isRunning())
            valueAnimator.cancel();

        valueAnimator = ValueAnimator.ofFloat(0, (CIRCLE_RADIUS * pixelToDen));

        valueAnimator.setDuration(300);

        valueAnimator.setInterpolator(interpolator);

        valueAnimator.addUpdateListener(this);

        valueAnimator.start();

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
//        setAlpha((int) (1f-animation.getAnimatedFraction()));
        float animatedRadiusValue = (float) animation.getAnimatedValue();
        if (animation.getAnimatedFraction() == 1)
            addCircle(0);
        else
            addCircle(animatedRadiusValue);
    }


    private void addCircle(float radiusValue) {
        mPath.reset();

        mPath.addCircle(circlePoint[0], circlePoint[1], radiusValue, CW);
        invalidateSelf();
    }
}
