package com.thugrzz.mypetapp.arch

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes

abstract class BaseBackStackBottomSheet(
    @LayoutRes private val layoutResId: Int,
    windowSizeRate: Float
) : DimensionalBottomSheetFragment(layoutResId, windowSizeRate) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnCancelListener {
            val fragmentManager = childFragmentManager
            if (fragmentManager.backStackEntryCount > 0) {
                fragmentManager.popBackStack()
            } else {
                it.cancel()
            }
        }
        return dialog
    }
}