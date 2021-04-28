package com.thugrzz.mypetapp.arch

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.ext.intPixelsOf

/**
 * Базовый класс для полноэкранного BottomSheetDialogFragment с верхним отступом
 *
 * @property marginTop отступ сверху в DP
 * @see setupBottomSheetHeight установка пиковой высоты диалога с учетом статус бара и верхнего отступа
 * @see getStatusBarHeight высота статус бара в пикселях
 * @see getWindowHeight высота экрана девайса в пикселях
 */

abstract class DimensionalBottomSheetFragment(
    @LayoutRes private val layoutRes: Int,
    private val marginTop: Float = 0f
) : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupBottomSheetHeight(bottomSheetDialog)
        }
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    private fun setupBottomSheetHeight(bottomSheetDialog: BottomSheetDialog) {
        val marginTopInPx = intPixelsOf(marginTop)
        val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet) as FrameLayout
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight() - getStatusBarHeight() - marginTopInPx
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.peekHeight = windowHeight
    }

    private fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return intPixelsOf(result.toFloat())
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
