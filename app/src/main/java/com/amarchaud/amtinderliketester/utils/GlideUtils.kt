package com.amarchaud.amtinderliketester.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class GlideUtils {

    companion object {

        fun createGlide(context : Context, path : String, dest : ImageView) {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            Glide
                .with(context)
                .load(path)
                .apply(
                    RequestOptions().override(400, 400).centerInside()
                        .placeholder(circularProgressDrawable)
                )
                //.placeholder(circularProgressDrawable)
                .into(dest)
        }
    }
}