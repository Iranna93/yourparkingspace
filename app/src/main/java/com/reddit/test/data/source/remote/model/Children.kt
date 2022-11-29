package com.reddit.test.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class Children(
    @SerializedName("kind") var kind: String,
    @SerializedName("data") var data: Data
)