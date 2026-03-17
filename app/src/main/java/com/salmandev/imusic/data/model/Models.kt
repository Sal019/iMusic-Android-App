package com.salmandev.imusic.data.model

data class Track(
    val id: String = "",
    val name: String = "",
    val artistId: String = "",
    val artistName: String = "",
    val albumId: String = "",
    val albumName: String = "",
    val albumArtUrl: String = "",
    val audioUrl: String = "",
    val duration: Int = 0,
    val shareUrl: String = "",
    val license: String = "",
    val genre: String = "",
    val releaseDate: String = "",
    val playCount: Long = 0L,
    val downloadCount: Long = 0L,
    val isFavorite: Boolean = false,
    val isDownloaded: Boolean = false,
    val localPath: String = "",
    val position: Int = 0
) {
    val durationFormatted: String
        get() {
            val mins = duration / 60
            val secs = duration % 60
            return String.format("%d:%02d", mins, secs)
        }
}

data class Artist(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val website: String = "",
    val joinDate: String = "",
    val country: String = "",
    val biography: String = "",
    val shareUrl: String = "",
    val trackCount: Int = 0,
    val albumCount: Int = 0
)

data class Album(
    val id: String = "",
    val name: String = "",
    val artistId: String = "",
    val artistName: String = "",
    val coverUrl: String = "",
    val releaseDate: String = "",
    val genre: String = "",
    val trackCount: Int = 0,
    val shareUrl: String = ""
)

data class Playlist(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val coverUrl: String = "",
    val trackCount: Int = 0,
    val tracks: List<Track> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val isUserCreated: Boolean = false
)

data class Lyrics(
    val trackId: String = "",
    val trackName: String = "",
    val artistName: String = "",
    val albumName: String = "",
    val plainLyrics: String = "",
    val syncedLyrics: String = ""
)

data class LyricsLine(
    val timeMs: Long,
    val text: String
)

sealed class RepeatMode {
    object None : RepeatMode()
    object One : RepeatMode()
    object All : RepeatMode()
}
