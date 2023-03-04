package com.ichi.moviedatabase.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ichi.moviedatabase.databinding.ViewHolderContainerItemBinding

class ListContainerAdapter(
    private val items: List<String>,
) : RecyclerView.Adapter<ListContainerAdapter.ViewHolder>() {
    override fun getItemCount() = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ViewHolderContainerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            val item = items[position]
            holder.bind(item)
        }
    }

    class ViewHolder(private val viewBinding: ViewHolderContainerItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(text: String) {
            viewBinding.textView.text = text
        }
    }
}
