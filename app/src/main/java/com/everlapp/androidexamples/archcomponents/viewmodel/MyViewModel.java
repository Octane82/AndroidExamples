package com.everlapp.androidexamples.archcomponents.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {        // extends AndroidViewModel - если нам нужен Context
                                                    // Context context = getApplication();

    MutableLiveData<String> data;

    public LiveData<String> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            loadData();
        }
        return data;
    }


    private void loadData() {
        // dataRepository.loadData              Load data Async
        data.postValue("String data");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // clean up resources
    }
}
