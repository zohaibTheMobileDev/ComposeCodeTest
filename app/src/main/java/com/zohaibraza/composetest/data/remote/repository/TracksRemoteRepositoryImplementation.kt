package com.zohaibraza.composetest.data.remote.repository

import com.zohaibraza.composetest.domain.remoterepository.TracksRemoteRepository
import com.zohaibraza.composetest.model.TrackResponse
import com.zohaibraza.composetest.data.remote.TracksApi
import retrofit2.Response

class TracksRemoteRepositoryImplementation(
    private val tracksApi: TracksApi
) : TracksRemoteRepository {

    override suspend fun fetchTracks(searchQuery: String): Response<TrackResponse> {
        return tracksApi.fetchTracks(searchQuery = searchQuery)
    }

}