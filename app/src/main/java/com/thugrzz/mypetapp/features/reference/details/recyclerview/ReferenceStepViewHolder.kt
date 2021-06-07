package com.thugrzz.mypetapp.features.reference.details.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.ReferenceItem
import com.thugrzz.mypetapp.databinding.ItemReferenceStepBinding
import com.thugrzz.mypetapp.ext.inflateHolder

class ReferenceStepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding by viewBinding(ItemReferenceStepBinding::bind)

    fun bind(item: ReferenceItem) = with(binding) {
        val context = itemView.context
        val position = bindingAdapterPosition + 1
        stepTitleView.text =
            context.getString(R.string.reference_step_title, position, item.stepTitle)
        descriptionView.text = item.description
        Glide.with(itemView.context)
            .load(item.imageUrl)
            .into(imageView)
    }

    companion object {

        fun create(parent: ViewGroup): ReferenceStepViewHolder {
            val itemView = parent.inflateHolder(R.layout.item_reference_step)
            return ReferenceStepViewHolder(itemView)
        }
    }
}