package me.martonpito.moviedbapp.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso
import me.martonpito.moviedbapp.R

fun ImageView.loadImage(url: String?) {
    url?.let {
        if (it.isEmpty()) {
            this.setImageResource((R.drawable.camera_placeholder))
        } else {
            Picasso.get().load(url).placeholder(R.drawable.camera_placeholder).into(this)
        }
    } ?: run {
        this.setImageResource((R.drawable.camera_placeholder))
    }
}