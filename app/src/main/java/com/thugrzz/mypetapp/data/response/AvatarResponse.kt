package com.thugrzz.mypetapp.data.response

import com.google.gson.annotations.SerializedName

data class AvatarResponse(
    @SerializedName("avatar_url") val avatarUrl: String
)