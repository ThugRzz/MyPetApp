package com.thugrzz.mypetapp.util

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Extension method to provide simpler access to {@link View#getResources()#getString(int)}.
 */
fun View.getString(stringResId: Int, vararg args: Any): String = resources.getString(stringResId, *args)

/**
 * Extension method to provide hide keyboard for [Activity].
 */
fun Activity.hideKeyboard() {
    currentFocus?.also { view ->
        val inputMethodManager = getSystemService(
            Context
            .INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

/**
 * Extension method to provide hide keyboard for [Fragment].
 */
fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

/**
 * Extension method to provide hide keyboard for [View].
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/**
 * Extension method to provide quicker access to the [LayoutInflater] from [Context].
 */
fun Context.getLayoutInflater() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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

/**
 * Extension method to provide simpler access to {@link ContextCompat#getColor(int)}.
 */
fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)

/**
 *  Extension method to provide simpler access to {@link ContextCompat#getColor(int)}
 *  from a [Fragment].
 */
fun Fragment.getColorCompat(color: Int) = context?.getColorCompat(color)

fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}