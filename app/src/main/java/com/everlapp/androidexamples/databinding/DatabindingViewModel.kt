package com.everlapp.androidexamples.databinding

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.everlapp.androidexamples.BR
import timber.log.Timber

class DatabindingViewModel :  ObservableViewModel() {

    val testInputText = MutableLiveData<String>()
    val user2 = ObservableField<User>()

    init {
        // Init observable Object
        user2.set(User("Ola", "Holopainen", 42))
    }


    var login: String = ""
        @Bindable get() {
            return field
        }
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.login  )
            }
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


    fun onClickText(view: View) {
        Timber.d("On click Text")
        user2.get()?.observableName?.set("Changed name !!!")
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