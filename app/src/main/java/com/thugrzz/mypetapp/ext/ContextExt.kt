package com.thugrzz.mypetapp.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat

fun Context.resolveAttribute(@AttrRes attrId: Int): Int {
    val outValue = TypedValue()
    theme.resolveAttribute(attrId, outValue, true)
    return outValue.resourceId
}

fun Context.resolveAttributeDrawable(@AttrRes attrId: Int): Drawable? {
    val resId = resolveAttribute(attrId)
    return ContextCompat.getDrawable(applicationContext, resId)
}

fun Context.getColorCompat(color: Int) = ContextCompat.getColor(this, color)

fun Context.getLayoutInflater() =
    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater