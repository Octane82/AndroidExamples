package com.everlapp.androidexamples.animations;

import android.animation.TypeEvaluator;

public class CustomEvaluator implements TypeEvaluator<Float> {

    @Override
    public Float evaluate(float fraction, Float startValue, Float endValue) {
        return startValue + fraction * (endValue - startValue);
    }
}
