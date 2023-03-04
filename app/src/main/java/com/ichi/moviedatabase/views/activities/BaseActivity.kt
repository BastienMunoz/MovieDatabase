package com.ichi.moviedatabase.views.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private lateinit var viewBinding: VB // Can be set to protected to be accessed from child classes

    abstract val bindingInflater: (LayoutInflater) -> VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = bindingInflater(layoutInflater)
        setContentView(viewBinding.root)
        onViewCreated()
    }

    protected open fun onViewCreated() {
        // Implemented in subclasses if needed
    }
}
