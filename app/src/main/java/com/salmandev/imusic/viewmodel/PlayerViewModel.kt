package com.salmandev.imusic.viewmodel

import android.content.ComponentName
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.salmandev.imusic.data.model.RepeatMode
import com.salmandev.imusic.data.model.Track
import com.salmandev.imusic.data.repository.MusicRepository
import com.salmandev.imusic.service.MusicPlaybackService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: MusicRepository
) : ViewModel() {

    private var controllerFuture: ListenableFuture<MediaController>? = null
    private var mediaController: MediaController? = null

    private val _currentTrack = MutableStateFlow<Track?>(null)
    val currentTrack: StateFlow<Track?> = _currentTrack.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _queue = MutableStateFlow<List<Track>>(emptyList())
    val queue: StateFlow<List<Track>> = _queue.asStateFlow()

    private val _isShuffled = MutableStateFlow(false)
    val isShuffled: StateFlow<Boolean> = _isShuffled.asStateFlow()

    private val _repeatMode = MutableStateFlow<RepeatMode>(RepeatMode.None)
    val repeatMode: StateFlow<RepeatMode> = _repeatMode.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _sleepTimerRemaining = MutableStateFlow(0L)
    val sleepTimerRemaining: StateFlow<Long> = _sleepTimerRemaining.asStateFlow()

    private val playerListener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            _isPlaying.value = mediaController?.isPlaying ?: false
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            mediaItem?.let { updateCurrentTrackFromMediaItem(it) }
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            _isShuffled.value = shuffleModeEnabled
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            _repeatMode.value = when (repeatMode) {
                Player.REPEAT_MODE_ONE -> RepeatMode.One
                Player.REPEAT_MODE_ALL -> RepeatMode.All
                else -> RepeatMode.None
            }
        }
    }

    init {
        initializeController()
        startPositionUpdater()
    }

    private fun initializeController() {
        val sessionToken = SessionToken(
            context,
            ComponentName(context, MusicPlaybackService::class.java)
        )
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture?.addListener({
            try {
                mediaController = controllerFuture?.get()
                mediaController?.addListener(playerListener)
                _isPlaying.value = mediaController?.isPlaying ?: false
                _isShuffled.value = mediaController?.shuffleModeEnabled ?: false
            } catch (e: Exception) {
                // Handle connection error gracefully
            }
        }, MoreExecutors.directExecutor())
    }

    private fun startPositionUpdater() {
        viewModelScope.launch {
            while (true) {
                mediaController?.let { controller ->
                    _currentPosition.value = controller.currentPosition
                    _duration.value = controller.duration.takeIf { it > 0 } ?: 0L
                }
                delay(500)
            }
        }
    }

    private fun updateCurrentTrackFromMediaItem(mediaItem: MediaItem) {
        val extras = mediaItem.mediaMetadata.extras
        val trackId = extras?.getString("track_id") ?: return
        val track = _queue.value.find { it.id == trackId }
        if (track != null) {
            _currentTrack.value = track
            viewModelScope.launch {
                _isFavorite.value = repository.isFavorite(track.id)
                repository.addToRecentlyPlayed(track)
            }
        }
    }

    fun playTrack(track: Track, queue: List<Track> = listOf(track)) {
        _queue.value = queue
        _currentTrack.value = track

        val mediaItems = queue.map { t ->
            val extras = android.os.Bundle().apply {
                putString("track_id", t.id)
                putString("artist_name", t.artistName)
                putString("album_name", t.albumName)
            }
            MediaItem.Builder()
                .setMediaId(t.id)
                .setUri(if (t.isDownloaded && t.localPath.isNotEmpty()) t.localPath else t.audioUrl)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(t.name)
                        .setArtist(t.artistName)
                        .setAlbumTitle(t.albumName)
                        .setArtworkUri(android.net.Uri.parse(t.albumArtUrl))
                        .setExtras(extras)
                        .build()
                )
                .setRequestMetadata(
                    MediaItem.RequestMetadata.Builder()
                        .setMediaUri(android.net.Uri.parse(
                            if (t.isDownloaded && t.localPath.isNotEmpty()) t.localPath else t.audioUrl
                        ))
                        .build()
                )
                .build()
        }

        val startIndex = queue.indexOfFirst { it.id == track.id }.coerceAtLeast(0)

        mediaController?.let { controller ->
            controller.setMediaItems(mediaItems, startIndex, 0)
            controller.prepare()
            controller.play()
        }

        viewModelScope.launch {
            _isFavorite.value = repository.isFavorite(track.id)
        }
    }

    fun togglePlayPause() {
        mediaController?.let { controller ->
            if (controller.isPlaying) controller.pause() else controller.play()
        }
    }

    fun skipToNext() { mediaController?.seekToNextMediaItem() }

    fun skipToPrevious() {
        mediaController?.let { controller ->
            if (controller.currentPosition > 3000) {
                controller.seekTo(0)
            } else {
                controller.seekToPreviousMediaItem()
            }
        }
    }

    fun seekTo(positionMs: Long) { mediaController?.seekTo(positionMs) }

    fun toggleShuffle() {
        mediaController?.let { controller ->
            controller.shuffleModeEnabled = !controller.shuffleModeEnabled
        }
    }

    fun toggleRepeat() {
        mediaController?.let { controller ->
            controller.repeatMode = when (controller.repeatMode) {
                Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_ALL
                Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ONE
                else -> Player.REPEAT_MODE_OFF
            }
        }
    }

    fun toggleFavorite() {
        val track = _currentTrack.value ?: return
        viewModelScope.launch {
            repository.toggleFavorite(track)
            _isFavorite.value = !_isFavorite.value
        }
    }

    fun startSleepTimer(minutes: Int) {
        if (minutes <= 0) {
            _sleepTimerRemaining.value = 0
            return
        }
        val totalMs = minutes * 60 * 1000L
        _sleepTimerRemaining.value = totalMs
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            while (_sleepTimerRemaining.value > 0) {
                delay(1000)
                val elapsed = System.currentTimeMillis() - startTime
                _sleepTimerRemaining.value = (totalMs - elapsed).coerceAtLeast(0)
                if (_sleepTimerRemaining.value <= 0) {
                    mediaController?.pause()
                }
            }
        }
    }

    fun addToQueue(track: Track) {
        val currentQueue = _queue.value.toMutableList()
        currentQueue.add(track)
        _queue.value = currentQueue
        val mediaItem = MediaItem.Builder()
            .setMediaId(track.id)
            .setUri(track.audioUrl)
            .build()
        mediaController?.addMediaItem(mediaItem)
    }

    override fun onCleared() {
        super.onCleared()
        MediaController.releaseFuture(controllerFuture ?: return)
    }
}
