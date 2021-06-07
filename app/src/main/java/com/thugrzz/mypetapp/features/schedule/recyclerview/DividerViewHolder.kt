package com.thugrzz.mypetapp.features.schedule.recyclerview

import android.view.View
import android.view.ViewGroup
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.ext.inflateHolder
import com.thugrzz.mypetapp.features.schedule.Item

class DividerViewHolder(
    itemView: View
) : BaseScheduleViewHolder(itemView) {

    override fun bind(item: Item) {
    }

    companion object {

        fun create(parent: ViewGroup): DividerViewHolder {
            val itemView = parent.inflateHolder(R.layout.item_schedule_divider)
            return DividerViewHolder(itemView)
        }
    }
}