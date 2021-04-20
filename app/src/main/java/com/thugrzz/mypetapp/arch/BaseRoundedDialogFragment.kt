package com.thugrzz.mypetapp.arch

import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.thugrzz.mypetapp.R

abstract class BaseRoundedDialogFragment(@LayoutRes layoutRes: Int) : BaseDialogFragment(layoutRes) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.RoundedCornerDialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window!!.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }
}