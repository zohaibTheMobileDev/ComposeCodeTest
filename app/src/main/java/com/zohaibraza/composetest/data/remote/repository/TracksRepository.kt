package com.zohaibraza.composetest.data.remote.repository

import com.zohaibraza.composetest.domain.remoterepository.TracksRemoteRepository
import com.zohaibraza.composetest.model.Track
import com.zohaibraza.composetest.model.TrackResponse
import com.zohaibraza.composetest.data.local.TrackDao
import javax.inject.Inject

class TracksRepository @Inject constructor(
    private val trackDao: TrackDao,
    private val apiService: TracksRemoteRepository // Retrofit API
) {

    // Search for tracks in Room based on the query
    suspend fun searchTracksLocally(query: String): List<Track> {
        return trackDao.searchTracks(query)
    }

    // Fetch data from API and cache it locally
    suspend fun fetchAndCacheTracks(query: String): TrackResponse? {
        return try {
            val apiResponse = apiService.fetchTracks(query)
            if (apiResponse.isSuccessful) {
                apiResponse.body()?.let { trackResponse ->
                    trackResponse.tracksList?.let {
                        // Cache API data in Room
                        trackDao.insertTracks(it)
                    }
                    trackResponse
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateFavouriteStatus(trackId: Int, isFavourite: Boolean) {
        trackDao.updateFavouriteStatus(trackId, isFavourite)
    }
}

