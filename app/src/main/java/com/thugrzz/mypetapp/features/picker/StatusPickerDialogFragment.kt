package com.thugrzz.mypetapp.features.picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.PetStatus
import com.thugrzz.mypetapp.databinding.DlgSexPickerBinding
import com.thugrzz.mypetapp.ext.enumArgument

class StatusPickerDialogFragment : BottomSheetDialogFragment() {

    interface Callback {

        fun onStatusPicked(status: PetStatus)
    }

    private val binding by viewBinding(DlgSexPickerBinding::bind)

    private val callback: Callback
        get() = targetFragment as Callback

    private var currentStatus by enumArgument<PetStatus>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.dlg_sex_picker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val values = PetStatus.values()
        val stringValues = values.map { getString(it.titleId) }
        val currentPickerValue = currentStatus.ordinal

        with(binding.valuePickerView) {
            minValue = 0
            maxValue = values.size - 1
            value = currentPickerValue
            wrapSelectorWheel = false
            displayedValues = stringValues.toTypedArray()
        }
        binding.buttonConfirm.setOnClickListener {
            val selectedIndex = binding.valuePickerView.value
            callback.onStatusPicked(values[selectedIndex])
            dismiss()
        }
    }

    companion object {

        val TAG = StatusPickerDialogFragment::class.simpleName!!

        fun show(
            target: Fragment,
            fragmentManager: FragmentManager,
            currentValue: PetStatus?,
        ) = StatusPickerDialogFragment().apply {
            setTargetFragment(target, 0)
            this.currentStatus = currentValue ?: PetStatus.CASTRATED
            show(fragmentManager, TAG)
        }
    }
}