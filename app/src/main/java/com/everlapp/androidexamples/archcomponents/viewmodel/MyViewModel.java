package com.everlapp.androidexamples.archcomponents.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {        // extends AndroidViewModel - если нам нужен Context
                                                    // Context context = getApplication();

    MutableLiveData<String> data;

    MutableLiveData<String> nameData = new MutableLiveData<>();


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


    public void setNameData(String name) {
        nameData.postValue(name);
    }


    public MutableLiveData<String> getNameData() {
        return nameData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // clean up resources
    }
}
