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
import com.thugrzz.mypetapp.data.model.remote.PetBreed
import com.thugrzz.mypetapp.databinding.DlgSexPickerBinding
import com.thugrzz.mypetapp.ext.nullableParcelableArgument
import com.thugrzz.mypetapp.ext.parcelableListArgument

class BreedPickerDialogFragment : BottomSheetDialogFragment() {

    interface Callback {

        fun onBreedPicked(breed: PetBreed)
    }

    private val binding by viewBinding(DlgSexPickerBinding::bind)

    private val callback: Callback
        get() = targetFragment as Callback

    private var currentType by nullableParcelableArgument<PetBreed>()
    private var values by parcelableListArgument<PetBreed>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.dlg_sex_picker, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stringValues = values.map { it.name }
        val currentPickerValue = 0

        with(binding.valuePickerView) {
            minValue = 0
            maxValue = values.size - 1
            value = currentPickerValue
            wrapSelectorWheel = false
            displayedValues = stringValues.toTypedArray()
        }
        binding.buttonConfirm.setOnClickListener {
            val selectedIndex = binding.valuePickerView.value
            callback.onBreedPicked(values[selectedIndex])
            dismiss()
        }
    }

    companion object {

        val TAG = BreedPickerDialogFragment::class.simpleName!!

        fun show(
            target: Fragment,
            fragmentManager: FragmentManager,
            values: List<PetBreed>,
            currentValue: PetBreed?,
        ) = BreedPickerDialogFragment().apply {
            setTargetFragment(target, 0)
            this.currentType = currentValue
            this.values = values
            show(fragmentManager, TAG)
        }
    }
}