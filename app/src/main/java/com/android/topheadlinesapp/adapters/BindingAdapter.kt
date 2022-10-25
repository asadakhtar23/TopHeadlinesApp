package com.android.topheadlinesapp.adapters

import android.webkit.WebView
import androidx.databinding.BindingAdapter

class BindingAdapter {
    companion object{

        @JvmStatic
        @BindingAdapter("loadUrl")
        fun WebView.setUrl(url: String) {
            this.loadUrl(url)
        }

    }

}