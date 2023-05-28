package com.konstandaki.drumtest.model

import com.squareup.moshi.Json

data class Event(
    @Json(name = "i") val id: String,
    @Json(name = "d") val name: String,
    @Json(name = "si") val sportId: String,
    @Json(name = "tt") val start: Long,
    var isFavorite: Boolean = false
)