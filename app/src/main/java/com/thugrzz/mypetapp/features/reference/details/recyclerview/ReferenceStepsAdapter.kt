package com.thugrzz.mypetapp.features.reference.details.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thugrzz.mypetapp.data.model.local.ReferenceItem

class ReferenceStepsAdapter : RecyclerView.Adapter<ReferenceStepViewHolder>() {

    private val items = mutableListOf<ReferenceItem>()

    fun setItems(items: List<ReferenceItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReferenceStepViewHolder.create(parent)

    override fun onBindViewHolder(holder: ReferenceStepViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size
}