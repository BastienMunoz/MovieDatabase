package com.ichi.moviedatabase.views.adapters

import androidx.recyclerview.widget.DiffUtil
import com.ichi.moviedatabase.viewmodels.models.AppHomeMovie

class PopularMoviesDiffUtil(
    private val oldList: List<AppHomeMovie>,
    private val newList: List<AppHomeMovie>,
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition].id == newList[newItemPosition].id
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
}
