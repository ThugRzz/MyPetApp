package com.thugrzz.mypetapp.ext

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TimePicker
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

fun TextInputEditText.clearError() {
    error = null
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Extension method to provide quicker access to the [LayoutInflater] from a [View].
 */
fun View.getLayoutInflater() = context.getLayoutInflater()

fun ViewGroup.inflateHolder(@LayoutRes layout: Int): View =
    getLayoutInflater().inflate(layout, this, false)

/**
 * Extension method to provide show keyboard for View.
 */
fun View.hide() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

/**
 * Extension method to provide show keyboard for View.
 */
fun View.show() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.visible(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.getString(@StringRes stringResId: Int, vararg args: Any): String =
    resources.getString(stringResId, *args)

fun MaterialTextView.setText(@StringRes resId: Int, vararg args: Any) {
    text = context.getString(resId, *args)
}

fun FloatingActionButton.setBehavior(dy: Int) {
    if (dy > 0 && isShown) hide() else if (dy < 0 && !isShown) show()
}

@Suppress("DEPRECATION")
var TimePicker.timeHour
    get() = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> hour
        else -> currentHour
    }
    set(value) = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> hour = value
        else -> currentHour = value
    }

@Suppress("DEPRECATION")
var TimePicker.timeMinute
    get() = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> minute
        else -> currentMinute
    }
    set(value) = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> minute = value
        else -> currentMinute = value
    }