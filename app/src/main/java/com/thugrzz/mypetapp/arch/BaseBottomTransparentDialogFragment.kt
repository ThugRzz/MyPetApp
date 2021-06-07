package com.thugrzz.mypetapp.arch

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.thugrzz.mypetapp.R


abstract class BaseBottomTransparentDialogFragment(@LayoutRes resId: Int) : BaseDialogFragment(resId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.TransparentDialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window!!.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.BOTTOM)
        }
    }
}