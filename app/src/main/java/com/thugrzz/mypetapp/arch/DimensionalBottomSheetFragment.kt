package com.thugrzz.mypetapp.arch

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thugrzz.mypetapp.R

abstract class DimensionalBottomSheetFragment(
    @LayoutRes private val resId: Int,
    private val windowSizeRate: Float
) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(resId, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = (getWindowHeight() * windowSizeRate).toInt()
        Log.e("window", "$windowHeight")
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.peekHeight = windowHeight
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            requireActivity().display?.getRealMetrics(displayMetrics)
        } else {
            @Suppress("DEPRECATION")
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        }
        return displayMetrics.heightPixels
    }
}