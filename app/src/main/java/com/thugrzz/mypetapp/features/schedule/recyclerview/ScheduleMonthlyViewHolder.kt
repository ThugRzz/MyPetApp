package com.thugrzz.mypetapp.features.schedule.recyclerview

import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.ItemScheduleMonthlyBinding
import com.thugrzz.mypetapp.ext.inflateHolder
import com.thugrzz.mypetapp.ext.setText
import com.thugrzz.mypetapp.features.schedule.Item
import com.thugrzz.mypetapp.util.DateUtil

class ScheduleMonthlyViewHolder(
    itemView: View
) : BaseScheduleViewHolder(itemView) {

    private val binding by viewBinding(ItemScheduleMonthlyBinding::bind)

    override fun bind(item: Item) = with(binding) {
        if (item is Item.MonthlyNotes) {
            val note = item.note
            val dayOfMonth = DateUtil.getDayOfMonth(note.date)
            val context = itemView.context
            val strokeColor = ContextCompat.getColor(context, note.type.colorRes)
            titleView.text = note.title
            descriptionView.text = note.description
            contentView.setStrokeColor(ColorStateList.valueOf(strokeColor))
            iconView.setImageDrawable(ContextCompat.getDrawable(context, note.type.iconRes))
            val time = note.time
            val stringTime =
                String.format(TIME_PATTERN, time.hours, time.minutes)
            dateView.setText(R.string.date_monthly, dayOfMonth, stringTime)
        }
    }

    companion object {

        private const val TIME_PATTERN = "%02d:%02d"

        fun create(parent: ViewGroup): ScheduleMonthlyViewHolder {
            val itemView = parent.inflateHolder(R.layout.item_schedule_monthly)
            return ScheduleMonthlyViewHolder(itemView)
        }
    }
}