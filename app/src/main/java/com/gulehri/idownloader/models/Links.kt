package com.gulehri.idownloader.models

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("Download High Quality")
    val highQuality: String,
    @SerializedName("Download Low Quality")
    val  lowQuality: String
)