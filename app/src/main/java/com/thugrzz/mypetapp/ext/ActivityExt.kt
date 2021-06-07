package com.thugrzz.mypetapp.ext

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    currentFocus?.also { view ->
        val inputMethodManager = getSystemService(
            Context
                .INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}