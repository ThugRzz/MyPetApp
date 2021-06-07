package com.thugrzz.mypetapp.features.reference.dialog

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textview.MaterialTextView
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.arch.BaseBottomTransparentDialogFragment
import com.thugrzz.mypetapp.databinding.DlgSortReferenceBinding
import com.thugrzz.mypetapp.ext.enumArgument

class FilterReferencesDialogFragment :
    BaseBottomTransparentDialogFragment(R.layout.dlg_sort_reference) {

    interface Callback {
        fun onFilterClick(filter: ReferencesFilter)
    }

    private val binding by viewBinding(DlgSortReferenceBinding::bind)
    private var selectedSort by enumArgument<ReferencesFilter>()

    private val callback: Callback
        get() = targetFragment as Callback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        bindSelectedSort()
        myPetFilterView.setOnClickListener { onFilterClicked(ReferencesFilter.MY_PET) }
        allFilterView.setOnClickListener { onFilterClicked(ReferencesFilter.ALL) }
        cancelButtonView.setOnClickListener { dismiss() }
    }

    private fun bindSelectedSort() = with(binding) {
        val selectedColor = ContextCompat.getColor(requireContext(), R.color.orange_CD5334)
        val textView: MaterialTextView = when (selectedSort) {
            ReferencesFilter.MY_PET -> myPetFilterView
            ReferencesFilter.ALL -> allFilterView
        }
        textView.setTextColor(selectedColor)
    }

    private fun onFilterClicked(filter: ReferencesFilter) {
        callback.onFilterClick(filter)
        dismiss()
    }

    companion object {

        val TAG = FilterReferencesDialogFragment::class.simpleName!!

        fun show(
            fragmentManager: FragmentManager,
            target: Fragment,
            selectedSort: ReferencesFilter
        ) = FilterReferencesDialogFragment().apply {
            setTargetFragment(target, 0)
            this.selectedSort = selectedSort
            show(fragmentManager, TAG)
        }
    }
}