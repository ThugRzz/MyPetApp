package com.thugrzz.mypetapp.features.reference.food.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thugrzz.mypetapp.data.model.local.ReferenceDetails

class FoodReferenceAdapter(
    private val onItemClick: (ReferenceDetails) -> Unit
) : RecyclerView.Adapter<FoodReferenceViewHolder>() {

    private val items = mutableListOf<ReferenceDetails>()

    fun setItems(items: List<ReferenceDetails>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FoodReferenceViewHolder.create(parent, onItemClick)

    override fun onBindViewHolder(holder: FoodReferenceViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size
}