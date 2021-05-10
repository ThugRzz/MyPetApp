package com.thugrzz.mypetapp.features.schedule.recyclerview

import android.content.res.ColorStateList
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.databinding.ItemScheduleDailyBinding
import com.thugrzz.mypetapp.ext.inflateHolder
import com.thugrzz.mypetapp.features.schedule.Item

class ScheduleDailyViewHolder(
    itemView: View
) : BaseScheduleViewHolder(itemView) {

    private val binding by viewBinding(ItemScheduleDailyBinding::bind)

    override fun bind(item: Item) = with(binding) {
        if (item is Item.DailyNote) {
            val note = item.note
            val context = itemView.context
            val strokeColor = ContextCompat.getColor(context, note.type.colorRes)
            titleView.text = note.title
            descriptionView.text = note.description
            contentView.setStrokeColor(ColorStateList.valueOf(strokeColor))
            iconView.setImageDrawable(ContextCompat.getDrawable(context, note.type.iconRes))
            val time = note.time
            val stringTime =
                String.format(TIME_PATTERN, time.hours, time.minutes)
            timeView.text = stringTime
        }
    }

    companion object {

        private const val TIME_PATTERN = "%02d:%02d"

        fun create(parent: ViewGroup): ScheduleDailyViewHolder {
            val itemView = parent.inflateHolder(R.layout.item_schedule_daily)
            return ScheduleDailyViewHolder(itemView)
        }
    }
}