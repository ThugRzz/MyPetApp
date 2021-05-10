package com.thugrzz.mypetapp.features.schedule

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.FmtScheduleBinding
import com.thugrzz.mypetapp.ext.collect
import com.thugrzz.mypetapp.ext.setBehavior
import com.thugrzz.mypetapp.features.schedule.add_schedule.AddScheduleDialog
import com.thugrzz.mypetapp.features.schedule.dialog.SortNotesDialog
import com.thugrzz.mypetapp.features.schedule.recyclerview.ScheduleAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleFragment : Fragment(R.layout.fmt_schedule),
    ScheduleAdapter.Callbacks,
    SortNotesDialog.Callback {

    private val binding by viewBinding(FmtScheduleBinding::bind)
    private val viewModel by viewModel<ScheduleViewModel>()

    private val adapter = ScheduleAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        scheduleView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )
        scheduleView.adapter = adapter
        scheduleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                addItemButton.setBehavior(dy)
            }
        })

        addItemButton.setOnClickListener {
            AddScheduleDialog.show(
                parentFragmentManager
            )
        }

        collect(viewModel.notesFlow, adapter::setItems)
        collect(viewModel.sortFlow, ::bindSelectedSort)
    }

    override fun onSortClick(sort: ScheduleSort) {
        viewModel.onSortSelected(sort)
    }

    override fun onItemCheckedClick(item: Item.NoteItem, isChecked: Boolean) {
        viewModel.onItemCheckedChange(item, isChecked)
    }

    private fun bindSelectedSort(sort: ScheduleSort) {
        binding.titleBarView.setEndIconClickListener {
            SortNotesDialog.show(parentFragmentManager, this@ScheduleFragment, sort)
        }
    }

    companion object {

        val TAG = ScheduleFragment::class.simpleName!!

        fun newInstance() = ScheduleFragment()
    }
}