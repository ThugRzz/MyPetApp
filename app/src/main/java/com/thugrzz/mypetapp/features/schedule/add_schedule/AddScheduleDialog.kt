package com.thugrzz.mypetapp.features.schedule.add_schedule

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.arch.DimensionalBottomSheetFragment
import com.thugrzz.mypetapp.data.model.local.NotePeriodicity
import com.thugrzz.mypetapp.data.model.local.NoteType
import com.thugrzz.mypetapp.data.model.local.Time
import com.thugrzz.mypetapp.databinding.DlgAddScheduleBinding
import com.thugrzz.mypetapp.ext.collect
import com.thugrzz.mypetapp.features.picker.SelectDateDialog
import com.thugrzz.mypetapp.features.picker.SelectTimeDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class AddScheduleDialog : DimensionalBottomSheetFragment(
    layoutRes = R.layout.dlg_add_schedule,
    marginTop = 32f
), SelectDateDialog.Callback,
    SelectTimeDialog.Callback {

    private val binding by viewBinding(DlgAddScheduleBinding::bind)
    private val viewModel by viewModel<AddScheduleViewModel>()

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        titleInputText.doOnTextChanged { text, _, _, _ -> viewModel.onTitleChange(text.toString()) }
        descriptionInputText.doOnTextChanged { text, _, _, _ -> viewModel.onDescriptionChange(text.toString()) }
        setupNoteTypeSelectedItemListener()
        setupNotePeriodicitySelecteditemListener()

        addNoteButton.setOnClickListener {
            viewModel.addNote()
            dismiss()
        }

        collect(viewModel.titleFlow, titleInputText::setTextKeepState)
        collect(viewModel.descriptionFlow, descriptionInputText::setTextKeepState)
        collect(viewModel.dateFlow, ::bindDate)
        collect(viewModel.timeFlow, ::bindTime)
        collect(viewModel.noteTypeFlow, ::bindNoteType)
        collect(viewModel.notePeriodicityFlow, ::bindNotePeriodicity)
    }

    private fun setupNoteTypeSelectedItemListener() {
        binding.noteTypeView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.onNoteTypeChange(NoteType.values()[position])
            }
        }
    }

    private fun setupNotePeriodicitySelecteditemListener() {
        binding.notePeriodicityView.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.onNotePeriodicityChange(NotePeriodicity.values()[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    override fun onDateSelected(dateInMillisecond: Long) {
        viewModel.onDateChange(dateInMillisecond)
    }

    override fun onSelectDateDialogDismissed() {
        binding.dateView.background = getInputBackgroundDrawable(false)
    }

    override fun onTimeSelected(time: Time) {
        viewModel.onTimeChange(time)
    }

    override fun onSelectTimeDialogDismissed() {
        binding.timeView.background = getInputBackgroundDrawable(false)
    }

    private fun bindDate(millis: Long) = with(binding.dateView) {
        if (millis != 0L) text = formatMillisToDate(millis)
        setOnClickListener {
            background = getInputBackgroundDrawable(true)
            SelectDateDialog.show(
                this@AddScheduleDialog,
                parentFragmentManager,
                millis
            )
        }
    }

    private fun bindTime(time: Time) = with(binding.timeView) {
        if (time != Time(0, 0)) text = getStringTime(time)
        setOnClickListener {
            background = getInputBackgroundDrawable(true)
            SelectTimeDialog.show(
                parentFragmentManager, this@AddScheduleDialog, time
            )
        }
    }

    private fun bindNoteType(noteType: NoteType) = with(binding.noteTypeView) {
        setSelection(noteType.ordinal, true)
    }

    private fun bindNotePeriodicity(notePeriodicity: NotePeriodicity) =
        with(binding.notePeriodicityView) {
            setSelection(notePeriodicity.ordinal, true)
        }

    private fun getInputBackgroundDrawable(isChosen: Boolean): Drawable? {
        val resId =
            if (isChosen) R.drawable.bg_input_field_picker_focused else R.drawable.bg_input_field_picker
        return ContextCompat.getDrawable(requireContext(), resId)
    }

    private fun getStringTime(time: Time) = String.format(TIME_PATTERN, time.hours, time.minutes)

    private fun formatMillisToDate(dateInMillis: Long): String = dateFormat.format(dateInMillis)

    companion object {

        private const val TIME_PATTERN = "%02d:%02d"
        val TAG = AddScheduleDialog::class.simpleName!!

        fun show(fragmentManager: FragmentManager) = AddScheduleDialog().show(fragmentManager, TAG)
    }
}