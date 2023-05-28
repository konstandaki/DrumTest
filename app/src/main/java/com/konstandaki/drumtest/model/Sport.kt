package com.konstandaki.drumtest.model

import com.squareup.moshi.Json

data class Sport(
    @Json(name = "i") val id: String,
    @Json(name = "d") val name: String,
    @Json(name = "e") val events: MutableList<Event>
)