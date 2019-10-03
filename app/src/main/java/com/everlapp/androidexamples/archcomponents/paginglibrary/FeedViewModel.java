package com.everlapp.androidexamples.archcomponents.paginglibrary;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.annotation.NonNull;

import com.everlapp.androidexamples.archcomponents.paginglibrary.datasource.FeedDataFactory;
import com.everlapp.androidexamples.archcomponents.paginglibrary.model.Article;
import com.everlapp.androidexamples.archcomponents.paginglibrary.utils.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FeedViewModel extends ViewModel {

    private Executor executor;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<Article>> articleLiveData;


    private AppController appController;
    public FeedViewModel(@NonNull AppController appController) {
        this.appController = appController;
        init();
    }

    private void init() {
        executor = Executors.newFixedThreadPool(5);

        FeedDataFactory feedDataFactory = new FeedDataFactory(appController);
        networkState = Transformations.switchMap(feedDataFactory.getMutableLiveData(),
                dataSource -> dataSource.getNetworkState());

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        articleLiveData = (new LivePagedListBuilder(feedDataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Article>> getArticleLiveData() {
        return articleLiveData;
    }
}
