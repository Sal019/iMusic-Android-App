package com.salmandev.imusic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salmandev.imusic.data.model.Album
import com.salmandev.imusic.data.model.Artist
import com.salmandev.imusic.data.model.Track
import com.salmandev.imusic.data.repository.MusicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MusicRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks.asStateFlow()

    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists: StateFlow<List<Artist>> = _artists.asStateFlow()

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _genreTracks = MutableStateFlow<List<Track>>(emptyList())
    val genreTracks: StateFlow<List<Track>> = _genreTracks.asStateFlow()

    private var searchJob: Job? = null

    init {
        // Debounced search
        viewModelScope.launch {
            _query
                .debounce(400)
                .distinctUntilChanged()
                .collect { q ->
                    if (q.length >= 2) performSearch(q)
                    else clearResults()
                }
        }
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    private fun performSearch(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _isLoading.value = true

            // Search tracks
            launch {
                repository.searchTracks(query).onSuccess {
                    _tracks.value = it
                }
            }

            // Search artists
            launch {
                repository.searchArtists(query).onSuccess {
                    _artists.value = it
                }
            }

            // Search albums
            launch {
                repository.searchAlbums(query).onSuccess {
                    _albums.value = it
                }
            }

            _isLoading.value = false
        }
    }

    fun searchByGenre(genre: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getTracksByGenre(genre).onSuccess {
                _genreTracks.value = it
            }
            _isLoading.value = false
        }
    }

    private fun clearResults() {
        _tracks.value = emptyList()
        _artists.value = emptyList()
        _albums.value = emptyList()
    }
}
