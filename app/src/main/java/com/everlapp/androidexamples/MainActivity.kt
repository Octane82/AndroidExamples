package com.everlapp.androidexamples

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.everlapp.androidexamples.registeraccount.ProfileDataSource
import com.everlapp.androidexamples.registeraccount.RegistrationCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val profileData = ProfileDataSource()
        profileData.createProfile("John", "qwerty", object : RegistrationCallback {
            override fun onSuccess(msg: String) {
                tvAndroidExamples.text = "Android examples: User register success!"
            }

            override fun onError(t: Throwable) {
                tvAndroidExamples.text = "Android examples: User REG Failure"
            }
        })


        tvAndroidExamples.animate().x(50f).y(100f).duration = 5000L

        val animSet = AnimatorInflater.loadAnimator(this, R.animator.property_animator) as AnimatorSet
        // animSet.setTarget(myObj)
        animSet.start()

    }


    fun onClickNext(view: View) {

    }

}
