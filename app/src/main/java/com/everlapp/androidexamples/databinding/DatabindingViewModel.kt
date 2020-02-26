package com.everlapp.androidexamples.databinding

import android.view.View
import androidx.lifecycle.ViewModel
import timber.log.Timber

class DatabindingViewModel : ViewModel() {

    fun onClickText(view: View) {
        Timber.d("On click Text")
    }

    fun onShowUser(user: User) {
        Timber.d("USER: $user")
    }

    fun onCheckboxChanged(user: User, isChecked: Boolean) {
        Timber.d("User:($user) isChecked: $isChecked")
    }

}