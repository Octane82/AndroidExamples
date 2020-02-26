package com.everlapp.androidexamples.databinding

import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import timber.log.Timber

class DatabindingViewModel : ObservableViewModel() {

    //val testInputText = MutableLiveData<String>()

    var testInputText: String
        @Bindable get() {
            return testInputText
        }
        set(value) {
            /*if (testInputText != value) {
                testInputText = value
            }*/
            //notifyPropertyChanged(BR.)
        }


    private val _likes =  MutableLiveData<Int>()

    // LiveData is preferred way with using databinding
    // access liveData inside .xml TextView value
    val likes: LiveData<Int> = _likes


    // Old method
    //var likes = 0
        //private set  // This is to prevent external modification of the variable.


    fun onLike() {
        Timber.e("OnLike click >>>")
        //likes++

        _likes.value = (_likes.value ?: 0) + 1
    }


    val popularity: LiveData<Popularity> = Transformations.map(_likes) {
        when {
            it > 9 -> Popularity.STAR
            it > 4 -> Popularity.POPULAR
            else -> Popularity.NORMAL
        }
    }

    /*@Bindable
    fun getTestInputTextO() : String {
        return testInputText
    }*/


    fun onClickText(view: View) {
        Timber.d("On click Text")
    }

    /**
     * Click button
     */
    fun onShowUser(user: User) {
        Timber.d("USER: $user")
    }

    fun onCheckboxChanged(user: User, isChecked: Boolean) {
        Timber.d("User:($user) isChecked: $isChecked")
    }

}