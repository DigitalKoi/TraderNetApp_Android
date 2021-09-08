package com.koidev.tradernet.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Subscribe(
    val type: String = "realtimeQuotes",
    @Json(name = "product_ids") val productIds: List<String>,
    val channels: List<String>,
    val obj: String
)
