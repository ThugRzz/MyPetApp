package com.thugrzz.mypetapp.features.schedule.recyclerview

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.ItemScheduleBinding
import com.thugrzz.mypetapp.ext.inflateHolder
import com.thugrzz.mypetapp.features.schedule.Item

class ScheduleOnceViewHolder(
    itemView: View,
    private val callbacks: ScheduleAdapter.Callbacks
) : BaseScheduleViewHolder(itemView) {

    private val binding by viewBinding(ItemScheduleBinding::bind)

    override fun bind(item: Item) = with(binding) {
        if (item is Item.NoteItem) {
            val note = item.note
            val isChecked = note.isChecked
            val context = itemView.context
            val strokeColor = ContextCompat.getColor(context, note.type.colorRes)
            titleView.text = note.title
            checkboxView.isChecked = isChecked
            descriptionView.text = note.description
            contentView.setStrokeColor(ColorStateList.valueOf(strokeColor))
            iconView.setImageDrawable(ContextCompat.getDrawable(context, note.type.iconRes))
            val time = note.time
            val stringTime = String.format(TIME_PATTERN, time.hours, time.minutes)
            dateView.text = stringTime
            checkboxView.setOnClickListener {
                callbacks.onItemCheckedClick(item, !isChecked)
            }
        }
    }

    companion object {

        private const val TIME_PATTERN = "%02d:%02d"

        fun create(
            parent: ViewGroup,
            callbacks: ScheduleAdapter.Callbacks
        ): ScheduleOnceViewHolder {
            val itemView = parent.inflateHolder(R.layout.item_schedule)
            return ScheduleOnceViewHolder(itemView, callbacks)
        }
    }
}