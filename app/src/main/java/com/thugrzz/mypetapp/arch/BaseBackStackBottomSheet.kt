package com.thugrzz.mypetapp.arch

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetDialog

abstract class BaseBackStackBottomSheet(
    @LayoutRes private val layoutResId: Int,
    windowSizeRate: Float
) : DimensionalBottomSheetFragment(layoutResId, windowSizeRate) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BackStackDialog(requireContext(), theme)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupBottomSheetHeight(bottomSheetDialog)
        }
        return dialog
    }

    private inner class BackStackDialog(
        context: Context,
        @StyleRes theme: Int
    ) : BottomSheetDialog(context, theme) {

        override fun onBackPressed() {
            val fragmentManager = childFragmentManager
            if (fragmentManager.backStackEntryCount > 0) {
                fragmentManager.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }
}