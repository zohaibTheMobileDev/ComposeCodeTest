package com.zohaibraza.composetest.model

import com.google.gson.annotations.SerializedName

class TrackResponse {

    // This param will have value only in case of error
    @SerializedName("errorMessage")
    var errorMessage: String? = null

    @SerializedName("resultCount")
    var resultCount: Int? = null

    @SerializedName("results")
    var tracksList: List<Track>? = null

}