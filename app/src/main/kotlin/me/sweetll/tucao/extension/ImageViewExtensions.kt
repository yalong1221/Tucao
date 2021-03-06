package me.sweetll.tucao.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import me.sweetll.tucao.GlideApp
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import me.sweetll.tucao.R

fun ImageView.load(context: Context, url: String): Unit {
    GlideApp.with(context)
            .load(url)
            .transition(withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(this)
}

fun ImageView.load(context: Context, url: String, errorRes: Int): Unit {
    GlideApp.with(context)
            .load(url)
            .transition(withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(RequestOptions.circleCropTransform())
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    return false
                }

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    GlideApp.with(context)
                            .load(errorRes)
                            .transition(withCrossFade())
                            .apply(RequestOptions.circleCropTransform())
                            .into(this@load)
                    return true
                }

            })
            .into(this)
}

fun ImageView.load(context: Context, bytes: ByteArray): Unit {
    GlideApp.with(context)
            .load(bytes)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .transition(withCrossFade())
            .into(this)
}

fun ImageView.load(context: Context, url: String, completeCallback: () -> Unit) {
    GlideApp.with(context)
            .load(url)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .onlyRetrieveFromCache(true)
            .listener(object: RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    completeCallback()
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    completeCallback()
                    return false
                }
            })
            .into(this)
}
