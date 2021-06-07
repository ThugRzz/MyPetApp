package com.thugrzz.mypetapp.features.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.ext.intArgument

class AlertDialogFragment : DialogFragment() {

    interface Callback {
        fun onAlertDialogAccepted(requestCode: Int)
    }

    private var messageId by intArgument()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setMessage(messageId)
            .setPositiveButton(R.string.ok) { _, _ ->
                (targetFragment as Callback).onAlertDialogAccepted(targetRequestCode)
            }
            .create()
    }

    companion object {

        private val TAG = AlertDialogFragment::class.simpleName

        fun show(
            requestCode: Int,
            target: Fragment,
            @StringRes messageId: Int,
        ) {
            AlertDialogFragment().apply {
                this.messageId = messageId
                setTargetFragment(target, requestCode)
                show(target.parentFragmentManager, TAG)
            }
        }
    }
}
