package com.chslcompany.spaceflightnews.core

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.Fragment

fun View.setOnSingleClickListener(func: () -> Unit) {
    setOnClickListener(object : OnSingleClickListener() {
        override fun onSingleClick(v: View?) {
            func.invoke()
        }
    })
}

abstract class OnSingleClickListener : View.OnClickListener {
    private var lastClickTime = 0L
    abstract fun onSingleClick(v: View?)
    override fun onClick(v: View?) {
        if (System.currentTimeMillis() - lastClickTime >= CLICK_INTERVAL) {
            lastClickTime = System.currentTimeMillis()
            onSingleClick(v)
        }
    }
    companion object {
        const val CLICK_INTERVAL = 1000
    }
}

fun Fragment?.openHyperlink(hyperlink: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(hyperlink))
    this?.startActivity(intent)
}