package com.salmandev.imusic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey val id: String,
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
    val isFavorite: Boolean = false,
    val isDownloaded: Boolean = false,
    val localPath: String = "",
    val addedAt: Long = System.currentTimeMillis(),
    val lastPlayed: Long = 0L,
    val playCount: Int = 0
)

@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String = "",
    val description: String = "",
    val coverUrl: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "playlist_tracks",
    primaryKeys = ["playlistId", "trackId"])
data class PlaylistTrackEntity(
    val playlistId: Long,
    val trackId: String,
    val position: Int = 0,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "recently_played")
data class RecentlyPlayedEntity(
    @PrimaryKey val trackId: String,
    val playedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "downloads")
data class DownloadEntity(
    @PrimaryKey val trackId: String,
    val trackName: String = "",
    val artistName: String = "",
    val albumArtUrl: String = "",
    val localPath: String = "",
    val fileSize: Long = 0L,
    val downloadedAt: Long = System.currentTimeMillis(),
    val status: String = "completed" // pending, downloading, completed, failed
)
