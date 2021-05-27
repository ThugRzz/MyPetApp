package com.thugrzz.mypetapp.features.reference.care.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thugrzz.mypetapp.data.model.local.ReferenceDetails

class CareReferenceAdapter(
    private val onItemClick: (ReferenceDetails) -> Unit
) : RecyclerView.Adapter<CareReferenceViewHolder>() {

    private val items = mutableListOf<ReferenceDetails>()

    fun setItems(items: List<ReferenceDetails>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CareReferenceViewHolder.create(parent, onItemClick)

    override fun onBindViewHolder(holder: CareReferenceViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size
}