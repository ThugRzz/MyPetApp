package com.thugrzz.mypetapp.features.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.Source

class FileSourceDialogFragment : DialogFragment() {

    interface Callback {
        fun onFileSourceChosen(source: Source)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setItems(R.array.chat_file_sources) { _, which ->
                val source = when (which) {
                    POSITION_CAMERA -> Source.CAMERA
                    POSITION_STORAGE -> Source.STORAGE
                    else -> throw IllegalStateException("Unexpected position $which")
                }
                (targetFragment as Callback).onFileSourceChosen(source)
            }
            .create()
    }

    companion object {

        private const val POSITION_STORAGE = 0
        private const val POSITION_CAMERA = 1

        private val TAG = FileSourceDialogFragment::class.simpleName

        fun show(target: Fragment) {
            FileSourceDialogFragment().apply {
                setTargetFragment(target, 0)
                show(target.parentFragmentManager, TAG)
            }
        }
    }
}