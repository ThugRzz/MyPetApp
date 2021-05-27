package com.thugrzz.mypetapp.features.reference.care.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.ReferenceDetails
import com.thugrzz.mypetapp.databinding.ItemCareReferenceBinding
import com.thugrzz.mypetapp.ext.inflateHolder

class CareReferenceViewHolder(
    itemView: View,
    private val onItemClick: (ReferenceDetails) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val binding by viewBinding(ItemCareReferenceBinding::bind)

    fun bind(item: ReferenceDetails) = with(binding) {
        titleView.text = item.title
        breedView.text = item.breed?.name
        petTypeView.text = item.petType?.type
        root.setOnClickListener { onItemClick(item) }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            onItemClick: (ReferenceDetails) -> Unit
        ): CareReferenceViewHolder {
            val itemView = parent.inflateHolder(R.layout.item_care_reference)
            return CareReferenceViewHolder(itemView, onItemClick)
        }
    }
}