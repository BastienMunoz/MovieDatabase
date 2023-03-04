package com.ichi.moviedatabase.views.listeners

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class EndlessRecyclerViewScrollListener(private val layoutManager: RecyclerView.LayoutManager) : RecyclerView.OnScrollListener() {
    private val visibleThreshold = 5

    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var isLoading = true
    private var startingPageIndex = 0

    abstract fun onLoadMore(page: Int, totalItemsCount: Int, recyclerView: RecyclerView)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = layoutManager.itemCount

        when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager -> {
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            }
        }

        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                isLoading = true
            }
        }

        if (isLoading && (totalItemCount > previousTotalItemCount)) {
            isLoading = false
            previousTotalItemCount = totalItemCount
        }

        if (!isLoading && ((lastVisibleItemPosition + visibleThreshold) > totalItemCount)) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, recyclerView)
            isLoading = true
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0

        for (i in lastVisibleItemPositions.indices) {
            if ((i == 0) || (lastVisibleItemPositions[i] > maxSize)) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }
}
