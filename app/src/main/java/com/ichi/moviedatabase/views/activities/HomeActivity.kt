package com.ichi.moviedatabase.views.activities

import android.view.LayoutInflater
import com.ichi.moviedatabase.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding = ActivityHomeBinding::inflate
}
