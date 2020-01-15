package com.everlapp.androidexamples.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 *
 * Example
rv_notifications.addOnScrollListener(object : RecyclerPaginationListener(layoutManager) {
    override fun loadMoreItems() {
        currOffset += 5   ---- Int
        Timber.e("(NEW) Load MORE Items. currOffset: $currOffset")

        //notificationsViewModel.requestNotifications(5, currOffset)
        //notificationsViewModel.postNotifications(5, currOffset)
    }

    override fun isLastPage(): Boolean {
        return notificationsViewModel.isLastPage
    }

    override fun isLoading(): Boolean {
        return notificationsViewModel.isLoading
    }
})
 *
 */
abstract class RecyclerPaginationListener(private val layoutManager: LinearLayoutManager) :
        RecyclerView.OnScrollListener() {


    companion object {
        val PAGE_START = 1
        val PAGE_SIZE = 5
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount

        val firstItemVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        // Timber.e("Listener: isLoading: ${isLoading()} isLastPage: ${isLastPage()}")
        if (!isLoading() && !isLastPage()) {
            //Timber.d("FIVP: $firstItemVisiblePosition TIC: $totalItemCount")
            if ((visibleItemCount + firstItemVisiblePosition) >= totalItemCount
                    && firstItemVisiblePosition >= 0
                    && totalItemCount >= PAGE_SIZE) {

                loadMoreItems()
            } else
                Timber.e("No LOAD Items")
        }

    }


    abstract fun loadMoreItems()

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

}
