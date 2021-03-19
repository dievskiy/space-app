package com.ruslan.assignment.view.adapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import com.ruslan.assignment.GlideApp
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("bindDate")
fun bindDate(text: TextView, date: Date?) {
    date ?: return
    try {
        val month = SimpleDateFormat("dd.MM")
        val dateText: String = month.format(date.time).toLowerCase(Locale.ROOT)
        text.text = dateText
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
fun imageUrl(
    imageView: ImageView,
    imageUrl: String?,
    placeholder: Drawable?,
) {
    when (imageUrl) {
        null -> {
            if (placeholder != null)
                GlideApp.with(imageView)
                    .load(placeholder)
                    .into(imageView)
        }
        else -> {
            val options = RequestOptions().centerCrop()
            GlideApp.with(imageView)
                .load(imageUrl)
                .placeholder(placeholder)
                .error(placeholder)
                .apply(options)
                .into(imageView)
        }
    }
}
