package com.reddit.test.data.source.remote.model

import com.google.gson.annotations.SerializedName


data class PostResponse(
    @SerializedName("kind") val kind: String,
    @SerializedName("data") val data: Data
) {
    data class Data(
        @SerializedName("after") val after: String,
        @SerializedName("dist") val dist: Int,
        @SerializedName("modhash") val modhash: String,
        @SerializedName("geo_filter") val geoFilter: String,
        @SerializedName("children") val children: ArrayList<Children>,
        @SerializedName("before") val before: String
    )
}
