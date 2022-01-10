package com.example.android.artistchalenge.ui

import android.widget.TextView
import androidx.core.view.isVisible

fun TextView.showAndSetOnNotNull(text: String?) {
    this.text = text
    isVisible = text != null
}