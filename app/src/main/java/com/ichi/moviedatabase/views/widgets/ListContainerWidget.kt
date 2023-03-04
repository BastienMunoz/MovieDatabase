package com.ichi.moviedatabase.views.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.ichi.moviedatabase.databinding.WidgetListContainerBinding
import com.ichi.moviedatabase.views.adapters.ListContainerAdapter

class ListContainerWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : BaseWidget<WidgetListContainerBinding>(context, attrs) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> WidgetListContainerBinding = WidgetListContainerBinding::inflate

    init {
        initWidget()
    }

    fun init(@StringRes titleResId: Int, items: List<String>) {
        viewBinding.titleTextView.text = context.getString(titleResId)
        viewBinding.itemsRecyclerView.layoutManager = LinearLayoutManager(context)
        viewBinding.itemsRecyclerView.adapter = ListContainerAdapter(items)
    }
}
