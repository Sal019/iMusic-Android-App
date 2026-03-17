package com.salmandev.imusic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmandev.imusic.data.model.Album
import com.salmandev.imusic.data.model.Artist
import com.salmandev.imusic.data.model.Track
import com.salmandev.imusic.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MusicRepository
) : ViewModel() {

    private val _trendingTracks = MutableStateFlow<List<Track>>(emptyList())
    val trendingTracks: StateFlow<List<Track>> = _trendingTracks.asStateFlow()

    private val _newReleases = MutableStateFlow<List<Track>>(emptyList())
    val newReleases: StateFlow<List<Track>> = _newReleases.asStateFlow()

    private val _featuredArtists = MutableStateFlow<List<Artist>>(emptyList())
    val featuredArtists: StateFlow<List<Artist>> = _featuredArtists.asStateFlow()

    private val _newAlbums = MutableStateFlow<List<Album>>(emptyList())
    val newAlbums: StateFlow<List<Album>> = _newAlbums.asStateFlow()

    private val _recentlyPlayed = MutableStateFlow<List<Track>>(emptyList())
    val recentlyPlayed: StateFlow<List<Track>> = _recentlyPlayed.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadHomeData()
        loadRecentlyPlayed()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            // Load trending tracks
            repository.getTrendingTracks(limit = 20).onSuccess {
                _trendingTracks.value = it
            }.onFailure {
                _error.value = it.message
            }

            // Load new releases
            repository.getNewReleases(limit = 10).onSuccess {
                _newReleases.value = it
            }

            // Load featured artists
            repository.getFeaturedArtists().onSuccess {
                _featuredArtists.value = it
            }

            // Load new albums
            repository.getNewAlbums().onSuccess {
                _newAlbums.value = it
            }

            _isLoading.value = false
        }
    }

    private fun loadRecentlyPlayed() {
        viewModelScope.launch {
            repository.getRecentlyPlayed().collect {
                _recentlyPlayed.value = it
            }
        }
    }
}
