package com.everlapp.androidexamples;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyCustomView extends View {

    boolean mTextVisible;



    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MyCustomView,
                0,
                0);
        try {
            mTextVisible = a.getBoolean(R.styleable.MyCustomView_textVisible, true);       // get attrib

        } finally {
            a.recycle();            // Обязательно ВЫЗВАТЬ !!!
        }
    }


    /**
     * Должен рассчитать размер View с учетом детей и значений аргументов
     * Обязательно вызвать метод: setMeasureDimension()
     * ОС будет знать, сколько отвести места под конкретное View
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        final int measureWidth = reconcileSize(getMeasuredWidth(), widthMeasureSpec);
        final int measureHeight = reconcileSize(getMeasuredHeight(), heightMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }


    /**
     * Reconcile a desired size for the view contents
     * @param contentSize
     * @param measureSpec
     * @return
     */
    private int reconcileSize(int contentSize, int measureSpec) {
        final int mode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY: return specSize;          // Точные значения layout width/height
            case MeasureSpec.AT_MOST:                           // Обычно matchParent or wrapContent. Установить размер не больше указанного
                if (contentSize < specSize) {
                    return contentSize;
                } else
                    return specSize;
            case MeasureSpec.UNSPECIFIED:                       // Обычно значит wrapContent. Можно указывать что угодно
            default:
                return contentSize;
        }
    }



    /**
     * Будет вызвано на изменение размера
     *
     * @param w     - новая ширина
     * @param h     - новая высота
     * @param oldw  - предыдущая ширина
     * @param oldh  - предыдущая высота
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * Вызывается на изменение прозрачности
     *
     * @param alpha - 0..255
     * @return true - если view может быть отрисовано с установленной alpha
     */
    @Override
    protected boolean onSetAlpha(int alpha) {
        return super.onSetAlpha(alpha);
    }

    /**
     * Отрисовка View
     * Canvas:
     *  - drawRect
     *  - drawOval
     *  - drawArc
     *  - drawLines
     *  - drawPoints
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int measWidth = getMeasuredWidth();
        int measHeight = getMeasuredHeight();

        // Draw something !!!

        invalidate();
    }





    // Custom setter
    public void setTextVisible(boolean mTextVisible) {
        this.mTextVisible = mTextVisible;
        invalidate();                                   // Необходимо перерисовать View
        requestLayout();                                // Если меняется размер
    }

    // Custom getter
    public boolean isTextVisible() {
        return mTextVisible;
    }

}
