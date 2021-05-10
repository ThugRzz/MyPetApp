package com.thugrzz.mypetapp.features.picker

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.DlgSelectDateBinding
import com.thugrzz.mypetapp.ext.longArgument
import java.util.*

class SelectDateDialog : BottomSheetDialogFragment() {

    interface Callback {
        fun onDateSelected(dateInMillisecond: Long)
        fun onSelectDateDialogDismissed()
    }

    private val binding by viewBinding(DlgSelectDateBinding::bind)

    private var dateInMillisecond by longArgument()

    private val callback: Callback
        get() = targetFragment as Callback

    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.dlg_select_date, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        calendar.timeZone = TimeZone.getDefault()

        setCurrentDate(dateInMillisecond)

        buttonConfirm.setOnClickListener {
            val selectDateInMilliseconds = formatDateToMilliseconds()
            callback.onDateSelected(selectDateInMilliseconds)
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback.onSelectDateDialogDismissed()
    }

    private fun setCurrentDate(currentDateInMillisecond: Long) = with(binding) {
        if (currentDateInMillisecond != 0L) {
            calendar.timeInMillis = currentDateInMillisecond
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        datePickerView.updateDate(year, month, dayOfMonth)
    }

    private fun formatDateToMilliseconds(): Long = with(binding) {
        val year = datePickerView.year
        val month = datePickerView.month
        val dayOfMonth = datePickerView.dayOfMonth
        calendar.set(year, month, dayOfMonth)
        return calendar.timeInMillis
    }

    companion object {

        val TAG = SelectDateDialog::class.simpleName!!

        fun show(
            target: Fragment,
            fragmentManager: FragmentManager,
            dateInMillisecond: Long
        ) = SelectDateDialog().apply {
            setTargetFragment(target, 1)
            this.dateInMillisecond = dateInMillisecond
            show(fragmentManager, TAG)
        }
    }
}