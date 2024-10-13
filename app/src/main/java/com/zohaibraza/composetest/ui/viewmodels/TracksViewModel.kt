package com.zohaibraza.composetest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zohaibraza.composetest.data.remote.repository.TracksRepository
import com.zohaibraza.composetest.model.LoadingState
import com.zohaibraza.composetest.model.Track
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class TracksViewModel @Inject constructor(
    private val tracksRepository: TracksRepository
) : ViewModel() {

    var detailTrack: Track? = null

    private val _searchQuery = MutableLiveData("star")
    val searchQuery: LiveData<String> get() = _searchQuery

    private val _fetchingTracksState = MutableLiveData<LoadingState>()
    val fetchingTracksState: LiveData<LoadingState> get() = _fetchingTracksState

    private val _trackResponse = MutableLiveData<List<Track>>()
    val trackResponse: LiveData<List<Track>> get() = _trackResponse

    // Fetch data based on search query
    fun fetchTracks() {
        val termQuery = _searchQuery.value ?: "star"

        viewModelScope.launch {
            _fetchingTracksState.postValue(LoadingState(inProgress = true))

            // First, search Room for local data matching the query
            val cachedTracks = tracksRepository.searchTracksLocally(termQuery)
            if (cachedTracks.isNotEmpty()) {
                // If local data is found, use it
                _trackResponse.postValue(cachedTracks)
                _fetchingTracksState.postValue(LoadingState(isSuccess = true, inProgress = false))
            } else {
                // If no local data, fetch from API and cache it
                val apiResponse = tracksRepository.fetchAndCacheTracks(termQuery)
                if (apiResponse != null) {
                    // Update LiveData with API data
                    _trackResponse.postValue(apiResponse.tracksList)
                    _fetchingTracksState.postValue(LoadingState(isSuccess = true, inProgress = false))
                } else {
                    // API failed and no local data
                    _fetchingTracksState.postValue(
                        LoadingState(
                            isSuccess = false,
                            inProgress = false,
                            errorMessage = "Failed to fetch data from API and no local data available"
                        )
                    )
                }
            }
        }
    }

    // Update the search query and trigger data fetching
    fun updateSearchQuery(query: String) {
        _searchQuery.postValue(query)
        fetchTracks()  // Fetch new data based on updated query
    }

    // Update the favorite status of a track
    fun toggleFavoriteStatus(track: Track) {
        viewModelScope.launch {
            // Update favorite status locally
            val updatedTrack = track.copy(isFavourite = !track.isFavourite)

            // Update in Room database
            tracksRepository.updateFavouriteStatus(updatedTrack.trackId!!, updatedTrack.isFavourite)

            _trackResponse.value = _trackResponse.value?.map {
                if (it.trackId == track.trackId) updatedTrack else it
            }
        }
    }
}



