package com.zohaibraza.composetest.data.remote

import com.zohaibraza.composetest.model.TrackResponse
import com.zohaibraza.composetest.data.constants.ApiUrls
import com.zohaibraza.composetest.data.constants.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TracksApi {

    @GET(ApiUrls.SEARCH)
    suspend fun fetchTracks(
        @Query(Constants.SEARCH_QUERY) searchQuery: String,
        @Query(Constants.COUNTRY) country: String = "us",
        @Query(Constants.MEDIA) mediaType: String = "movie",
        @Query(Constants.PAGE_SIZE) pageSize: Int = 50,
    ): Response<TrackResponse>

}