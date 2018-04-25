package com.everlapp.androidexamples.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;

public class AnimatorsExample {


    public void animators() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(1000);
        valueAnimator.start();

        // Object animators as same !!!
        // "alpha" - change field
        // ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(new Dog(), "alpha", 0f, 1f);

        // animator listener
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        AnimatorSet bouncer = new AnimatorSet();
        bouncer.play(valueAnimator); //.before()

        AnimatorSet as = new AnimatorSet();
        as.play(bouncer);
        as.start();

    }

}
