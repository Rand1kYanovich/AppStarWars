package com.example.startwarsapp

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Pagination class to add more items to the list when reach the last item.
 */
abstract class PaginationScrollListener
/**
 * Supporting only LinearLayoutManager for now.
 *
 * @param layoutManager
 */
    (var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()


        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount-1 && firstVisibleItemPosition >= 0) {
                loadMoreItems()

            }
        }
    }
    abstract fun loadMoreItems()
}