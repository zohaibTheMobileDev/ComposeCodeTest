package com.zohaibraza.composetest.domain.remoterepository

import com.zohaibraza.composetest.model.TrackResponse
import retrofit2.Response

interface TracksRemoteRepository {

    suspend fun fetchTracks(searchQuery: String) : Response<TrackResponse>

}