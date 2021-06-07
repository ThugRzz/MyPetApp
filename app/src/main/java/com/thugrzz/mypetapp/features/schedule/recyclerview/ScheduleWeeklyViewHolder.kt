package com.thugrzz.mypetapp.features.schedule.recyclerview

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.ItemScheduleWeeklyBinding
import com.thugrzz.mypetapp.ext.inflateHolder
import com.thugrzz.mypetapp.ext.setText
import com.thugrzz.mypetapp.features.schedule.Item
import com.thugrzz.mypetapp.util.DateUtil

class ScheduleWeeklyViewHolder(
    itemView: View
) : BaseScheduleViewHolder(itemView) {

    private val binding by viewBinding(ItemScheduleWeeklyBinding::bind)

    override fun bind(item: Item) = with(binding) {
        if (item is Item.WeeklyNote) {
            val note = item.note
            val date = note.date
            val dayOfWeek = DateUtil.getDayOfWeek(date)
            val context = itemView.context
            val strokeColor = ContextCompat.getColor(context, note.type.colorRes)
            titleView.text = note.title
            descriptionView.text = note.description
            contentView.setStrokeColor(ColorStateList.valueOf(strokeColor))
            iconView.setImageDrawable(ContextCompat.getDrawable(context, note.type.iconRes))
            val time = note.time
            val stringTime =
                String.format(TIME_PATTERN, time.hours, time.minutes)
            val stringDayOfWeek = context.getString(dayOfWeek.titleRes)
            dateView.setText(R.string.date_weekly, stringDayOfWeek, stringTime)
        }
    }

    companion object {

        private const val TIME_PATTERN = "%02d:%02d"

        fun create(parent: ViewGroup): ScheduleWeeklyViewHolder {
            val itemView = parent.inflateHolder(R.layout.item_schedule_weekly)
            return ScheduleWeeklyViewHolder(itemView)
        }
    }
}