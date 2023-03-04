package com.ichi.moviedatabase.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ichi.moviedatabase.R

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _viewBinding: VB? = null
    protected val viewBinding get() = _viewBinding!! // Only valid between onCreateView() and onDestroyView()

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = bindingInflater(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    protected open fun onViewCreated() {
        // Implemented in subclasses if needed
    }

    protected fun showErrorDialog(@StringRes errorResId: Int) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.error_title)
                .setMessage(errorResId)
                .setPositiveButton(android.R.string.ok, null)
                .show()
        }
    }
}
