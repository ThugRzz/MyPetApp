package com.thugrzz.mypetapp.features.schedule.dialog

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.textview.MaterialTextView
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.arch.BaseBottomTransparentDialogFragment
import com.thugrzz.mypetapp.databinding.DlgSortNotesBinding
import com.thugrzz.mypetapp.ext.enumArgument
import com.thugrzz.mypetapp.features.schedule.ScheduleSort

class SortNotesDialog : BaseBottomTransparentDialogFragment(R.layout.dlg_sort_notes) {

    interface Callback {
        fun onSortClick(sort: ScheduleSort)
    }

    private val binding by viewBinding(DlgSortNotesBinding::bind)
    private var selectedSort by enumArgument<ScheduleSort>()

    private val callback: Callback
        get() = targetFragment as Callback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        bindSelectedSort()
        todaySortView.setOnClickListener { onSortClicked(ScheduleSort.ONCE) }
        dailySortView.setOnClickListener { onSortClicked(ScheduleSort.DAILY) }
        weeklySortView.setOnClickListener { onSortClicked(ScheduleSort.WEEKLY) }
        monthlySortView.setOnClickListener { onSortClicked(ScheduleSort.MONTHLY) }
        cancelButtonView.setOnClickListener { dismiss() }
    }

    private fun bindSelectedSort() = with(binding) {
        val selectedColor = ContextCompat.getColor(requireContext(), R.color.orange_CD5334)
        val selectedColorStateList = ColorStateList.valueOf(selectedColor)
        val textView: MaterialTextView = when (selectedSort) {
            ScheduleSort.ONCE -> todaySortView
            ScheduleSort.DAILY -> dailySortView
            ScheduleSort.WEEKLY -> weeklySortView
            ScheduleSort.MONTHLY -> monthlySortView
        }
        textView.setTextColor(selectedColor)
        TextViewCompat.setCompoundDrawableTintList(textView, selectedColorStateList)
    }

    private fun onSortClicked(sort: ScheduleSort) {
        callback.onSortClick(sort)
        dismiss()
    }

    companion object {

        val TAG = SortNotesDialog::class.simpleName!!

        fun show(
            fragmentManager: FragmentManager,
            target: Fragment,
            selectedSort: ScheduleSort
        ) = SortNotesDialog().apply {
            setTargetFragment(target, 0)
            this.selectedSort = selectedSort
            show(fragmentManager, TAG)
        }
    }
}