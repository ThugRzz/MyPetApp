package com.thugrzz.mypetapp.util.reference

import com.thugrzz.mypetapp.R
import com.thugrzz.mypetapp.data.model.local.ReferenceData

object ReferencesData {

    val referenceData = listOf<ReferenceData>(
        ReferenceData(id = 1, titleResId = R.string.feed, iconResId = R.mipmap.feed),
        ReferenceData(id = 2, titleResId = R.string.pet_care, iconResId = R.mipmap.care),
        ReferenceData(id = 3, titleResId = R.string.training, iconResId = R.mipmap.training),
        ReferenceData(id = 4, titleResId = R.string.disease, iconResId = R.mipmap.disease),
    )
}