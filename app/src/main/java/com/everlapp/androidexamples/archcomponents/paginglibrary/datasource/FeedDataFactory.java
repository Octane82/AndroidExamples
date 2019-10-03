package com.everlapp.androidexamples.archcomponents.paginglibrary.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.everlapp.androidexamples.archcomponents.paginglibrary.AppController;

public class FeedDataFactory extends DataSource.Factory {

    private MutableLiveData<FeedDataSource> mutableLiveData;
    private FeedDataSource feedDataSource;
    private AppController appController;

    public FeedDataFactory(AppController appController) {
        this.appController = appController;
        this.mutableLiveData = new MutableLiveData<FeedDataSource>();
    }

    @Override
    public DataSource create() {
        feedDataSource = new FeedDataSource(appController);
        mutableLiveData.postValue(feedDataSource);
        return feedDataSource;
    }


    public MutableLiveData<FeedDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
