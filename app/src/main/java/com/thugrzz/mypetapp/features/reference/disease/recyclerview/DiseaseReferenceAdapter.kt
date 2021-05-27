package com.thugrzz.mypetapp.features.reference.disease.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thugrzz.mypetapp.data.model.local.ReferenceDetails

class DiseaseReferenceAdapter(
    private val onItemClick: (ReferenceDetails) -> Unit
) : RecyclerView.Adapter<DiseaseReferenceViewHolder>() {

    private val items = mutableListOf<ReferenceDetails>()

    fun setItems(items: List<ReferenceDetails>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DiseaseReferenceViewHolder.create(parent, onItemClick)

    override fun onBindViewHolder(holder: DiseaseReferenceViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size
}