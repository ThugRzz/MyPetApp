package com.thugrzz.mypetapp.data.model.local

import android.net.Uri

data class CropAvatar(
    val sourceImageUri: Uri,
    val croppedImageUri: Uri
)