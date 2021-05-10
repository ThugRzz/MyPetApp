package com.thugrzz.mypetapp.features.picker

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.Time
import com.thugrzz.mypetapp.databinding.DlgSelectTimeBinding
import com.thugrzz.mypetapp.ext.parcelableArgument
import com.thugrzz.mypetapp.ext.timeHour
import com.thugrzz.mypetapp.ext.timeMinute

class SelectTimeDialog : BottomSheetDialogFragment() {

    interface Callback {
        fun onTimeSelected(time: Time)
        fun onSelectTimeDialogDismissed()
    }

    private val binding by viewBinding(DlgSelectTimeBinding::bind)

    private var currentTime by parcelableArgument<Time>()

    private val callback: Callback
        get() = targetFragment as Callback

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dlg_select_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        timePickerView.setIs24HourView(true)
        setCurrentTime()

        buttonConfirm.setOnClickListener {
            val time = Time(
                hours = timePickerView.timeHour,
                minutes = timePickerView.timeMinute
            )
            callback.onTimeSelected(time)
            dismiss()
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        callback.onSelectTimeDialogDismissed()
    }

    private fun setCurrentTime() = with(binding.timePickerView) {
        timeHour = currentTime.hours
        timeMinute = currentTime.minutes
    }

    companion object {

        val TAG = SelectTimeDialog::class.simpleName!!

        fun show(
            fragmentManager: FragmentManager,
            target: Fragment,
            currentTime: Time = Time(hours = 0, minutes = 0)
        ) = SelectTimeDialog().apply {
            this.currentTime = currentTime
            setTargetFragment(target, 0)
            show(fragmentManager, TAG)
        }
    }
}