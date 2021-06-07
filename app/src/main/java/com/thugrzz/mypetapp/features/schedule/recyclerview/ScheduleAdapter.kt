package com.thugrzz.mypetapp.features.schedule.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thugrzz.mypetapp.features.schedule.Item

class ScheduleAdapter(
    private val callbacks: Callbacks
) : RecyclerView.Adapter<BaseScheduleViewHolder>() {

    interface Callbacks {
        fun onItemCheckedClick(item: Item.NoteItem, isChecked: Boolean)
    }

    private val items = mutableListOf<Item>()

    fun setItems(items: List<Item>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_NOTE_ONCE -> ScheduleOnceViewHolder.create(parent, callbacks)
        TYPE_NOTE_DAILY -> ScheduleDailyViewHolder.create(parent)
        TYPE_NOTE_WEEKLY -> ScheduleWeeklyViewHolder.create(parent)
        TYPE_NOTE_MONTHLY -> ScheduleMonthlyViewHolder.create(parent)
        TYPE_DIVIDER_DONE -> DividerViewHolder.create(parent)
        else -> throw IllegalArgumentException("Invalid view type")
    }

    override fun onBindViewHolder(holder: BaseScheduleViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is Item.NoteItem -> TYPE_NOTE_ONCE
        is Item.DailyNote -> TYPE_NOTE_DAILY
        is Item.WeeklyNote -> TYPE_NOTE_WEEKLY
        is Item.MonthlyNotes -> TYPE_NOTE_MONTHLY
        is Item.Divider -> TYPE_DIVIDER_DONE
    }

    override fun getItemCount() = items.size

    companion object {
        private const val TYPE_NOTE_ONCE = 1
        private const val TYPE_NOTE_DAILY = 2
        private const val TYPE_NOTE_WEEKLY = 3
        private const val TYPE_NOTE_MONTHLY = 4
        private const val TYPE_DIVIDER_DONE = 5
        private const val TYPE_DIVIDER_DAILY = 6
        private const val TYPE_DIVIDER_WEEKLY = 7
        private const val TYPE_DIVIDER_MONTHLY = 8
    }
}