package com.thugrzz.mypetapp.ext

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

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
    this.setText(resId, *args)
}

fun TextInputEditText.asFlow(resetTarget: () -> Unit): Flow<EditText> {
    return Channel<EditText>(capacity = Channel.UNLIMITED).also { channel ->
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    this@asFlow.clearError()
                    resetTarget()
                    channel.offer(this@asFlow)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }.receiveAsFlow()
}

suspend fun EditText.isValidSize(): Boolean {
    if (text.length < 5) {
        error = "Invalid Size"
        return false
    }
    return true
}