package com.ichi.moviedatabase.core.imageloader

import android.widget.ImageView
import coil.load

class ImageLoader : ImageLoaderInterface {
    override fun loadImage(imageView: ImageView, url: String, params: ImageLoaderInterface.Parameters) {
        imageView.load(url) {
            params.errorResId?.let { error(it) }
            params.placeholderResId?.let { placeholder(it) }
        }
    }
}
