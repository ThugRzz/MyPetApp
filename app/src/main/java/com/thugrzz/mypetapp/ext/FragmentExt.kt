package com.thugrzz.mypetapp.ext

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.showIfNotShowing(fragmentManager: FragmentManager, tag: String) {
    if (fragmentManager.findFragmentByTag(tag) == null) {
        show(fragmentManager, tag)
    }
}