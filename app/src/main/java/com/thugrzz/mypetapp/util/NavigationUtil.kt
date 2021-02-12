package com.thugrzz.mypetapp.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.thugrzz.mypetapp.R

object NavigationUtil {

    fun showNextFragment(
        parentFragmentManager: FragmentManager,
        container:Int,
        fragmentInstance: Fragment,
        fragmentTag: String
    ) = parentFragmentManager.commit {
        setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
        replace(
            container,
            fragmentInstance,
            fragmentTag
        )
    }

    fun showPrevFragment(
        parentFragmentManager: FragmentManager,
        container:Int,
        fragmentInstance: Fragment,
        fragmentTag: String
    ) = parentFragmentManager.commit {
        setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        replace(
            container,
            fragmentInstance,
            fragmentTag
        )
    }
}