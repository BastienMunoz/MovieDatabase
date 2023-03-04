package com.ichi.moviedatabase.core.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoaderInterface {
    data class Parameters(
        @DrawableRes val errorResId: Int? = null,
        @DrawableRes val placeholderResId: Int? = null,
    )

    fun loadImage(imageView: ImageView, url: String, params: Parameters = Parameters())
}
