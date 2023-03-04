package com.ichi.moviedatabase.views.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding

abstract class BaseWidget<VB: ViewBinding>(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {
    private var _viewBinding: VB? = null
    protected val viewBinding get() = _viewBinding!!

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected fun initWidget() {
        _viewBinding = bindingInflater(LayoutInflater.from(context), this, true)
    }
}
