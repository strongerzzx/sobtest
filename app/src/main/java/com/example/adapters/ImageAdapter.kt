package com.example.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerAdapter


class ImageAdapter(bannerUrl: List<String>) :
    BannerAdapter<String, ImageAdapter.ImageHolder>(bannerUrl) {

    class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view as ImageView
    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val iv = ImageView(parent?.context)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        iv.layoutParams = params
        iv.scaleType = ImageView.ScaleType.CENTER_CROP
        return ImageHolder(iv)
    }

    override fun onBindView(holder: ImageHolder, data: String, position: Int, size: Int) {
        Glide.with(holder.itemView)
            .load(data)
            .into(holder.imageView)
    }
}