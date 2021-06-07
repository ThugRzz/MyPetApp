package com.thugrzz.mypetapp.features.reference.training.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.ReferenceDetails
import com.thugrzz.mypetapp.databinding.ItemTrainingReferenceBinding
import com.thugrzz.mypetapp.ext.inflateHolder

class TrainingReferenceViewHolder(
    itemView: View,
    private val onItemClick: (ReferenceDetails) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val binding by viewBinding(ItemTrainingReferenceBinding::bind)

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
        ): TrainingReferenceViewHolder {
            val itemView = parent.inflateHolder(R.layout.item_training_reference)
            return TrainingReferenceViewHolder(itemView, onItemClick)
        }
    }
}