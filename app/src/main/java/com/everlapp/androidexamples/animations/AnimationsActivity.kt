package com.everlapp.androidexamples.animations

import android.animation.*
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.transition.*
import com.everlapp.androidexamples.R
import kotlinx.android.synthetic.main.activity_animation_property.*
import kotlinx.android.synthetic.main.activity_animation_transition_one.*
import kotlinx.android.synthetic.main.activity_animation_transition_two.*

/**
 * https://developer.android.com/training/animation
 *
 * https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-property-animation/#0
 * https://codelabs.developers.google.com/codelabs/motion-layout/#2
 *
 * https://www.raywenderlich.com/1376936-android-transition-framework-getting-started
 *
 * - Object animator
 * - Transition framework  (Scene)
 * - Activity animations ( ActivityOptions.makeSceneTransitionAnimation() )
 *
 * Fragment transition
 * - https://medium.com/@bherbst/fragment-transitions-with-shared-elements-7c7d71d31cbb
 * - https://medium.com/bynder-tech/how-to-use-material-transitions-in-fragment-transactions-5a62b9d0b26b
 */
class AnimationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_constraint_layout)

        // 0 - object animator
        // 1- transition framework
        val animationMode = 3

        when (animationMode) {
            0 -> {
                // Object animator
                setContentView(R.layout.activity_animation_property)
                btnRotate.setOnClickListener { rotater() }
                btnTranslate.setOnClickListener { translater() }
                btnScale.setOnClickListener { scaler() }
                btnFading.setOnClickListener { fader() }
                btnColorize.setOnClickListener { colorizer() }
                btnShower.setOnClickListener { shower() }
            }
            1 -> {
                // Transition framework
                setContentView(R.layout.activity_animation_transition_one)
                btnAnimate.setOnClickListener { transitionOne() }
            }
            2 -> {
                setContentView(R.layout.activity_animation_transition_two)
                btn_change_scene.setOnClickListener { transitionTwo() }
            }
            3 -> {
                setContentView(R.layout.activity_animation_fragment_transitions)
                if (savedInstanceState == null) {
                    supportFragmentManager.beginTransaction()
                            .add(R.id.fragment_container, GridFragment())
                            .commit()
                }
            }
        }
    }


    // ---------- Fragment transition with shared elements ------





    // ---------- Transition framework ----------------

    private fun transitionOne() {
        // вызываем метод, говорящий о том, что мы хотим анимировать следующие изменения внутри sceneRoot
        TransitionManager.beginDelayedTransition(container)

        val params = transition_square.layoutParams
        val newSquareSize = resources.getDimensionPixelSize(R.dimen.square_size_expand)
        params.width = newSquareSize
        params.height = newSquareSize
        transition_square.layoutParams = params

        // Transition type
        // - Change bounds
        // - Fade
        // - Transition set
        // - Auto transition
    }


    private fun transitionTwo() {
        val scene2 = Scene.getSceneForLayout(scene_root, R.layout.animation_scene2, this)
        // опишем свой аналог AutoTransition
        val set = TransitionSet()
        set.addTransition(Fade())
        set.addTransition(ChangeBounds())
        // выполняться они будут одновременно
        set.ordering = TransitionSet.ORDERING_TOGETHER
        // уставим свою длительность анимации
        set.duration = 500
        // и изменим Interpolator
        set.interpolator = AccelerateInterpolator()
        TransitionManager.go(scene2, set)
    }




    // ------ Animation property ---------

    private fun rotater() {
        // -360 start => 0 end (full circle)
        val animator = ObjectAnimator.ofFloat(star, View.ROTATION, -360f, 0f)
        animator.duration = 1000
        animator.disableViewDuringAnimation(btnRotate)
        /*animator.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                btnRotate.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                btnRotate.isEnabled = true
            }
        })*/
        animator.start()
    }

    private fun translater() {
        // 200f - pixels
        val animator = ObjectAnimator.ofFloat(star, View.TRANSLATION_X, 200f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(btnTranslate)
        animator.start()
    }

    private fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(star, scaleX, scaleY)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(btnScale)
        animator.start()
    }

    private fun fader() {
        val animator = ObjectAnimator.ofFloat(star, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(btnFading)
        animator.start()
    }

    private fun colorizer() {
        //val animator = ObjectAnimator.ofInt(star.parent, "backgroundColor", Color.BLACK, Color.RED).start()

        // more smooth animation
        var animator = ObjectAnimator.ofArgb(star.parent, "backgroundColor", Color.BLACK, Color.RED)
        animator.duration = 500
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(btnColorize)
        animator.start()
    }

    private fun shower() {
        val container = star.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height
        var starW: Float = star.width.toFloat()
        var starH: Float = star.height.toFloat()
        // Create new star
        val newStar = AppCompatImageView(this)
        newStar.setImageResource(R.drawable.ic_star_outline)
        newStar.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT)
        container.addView(newStar)

        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY
        // Position the new star (initial star information)
        newStar.translationX = Math.random().toFloat() * containerW - starW / 2

        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)
        val rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION, (Math.random() * 1080).toFloat())
        rotator.interpolator = LinearInterpolator()
        // Run the animations in parallel
        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 1500 + 500).toLong()
        set.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                container.removeView(newStar)
            }
        })
        set.start()
    }


    // extension helper method
    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                view.isEnabled = true
            }
        })
    }


}