package com.thugrzz.mypetapp.features.schedule.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.thugrzz.mypetapp.features.schedule.Item

abstract class BaseScheduleViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: Item)
}