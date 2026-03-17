package com.salmandev.imusic.data.repository

import com.salmandev.imusic.core.Constants
import com.salmandev.imusic.data.api.JamendoApiService
import com.salmandev.imusic.data.api.LrcLibApiService
import com.salmandev.imusic.data.local.dao.DownloadDao
import com.salmandev.imusic.data.local.dao.PlaylistDao
import com.salmandev.imusic.data.local.dao.TrackDao
import com.salmandev.imusic.data.local.entity.*
import com.salmandev.imusic.data.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepository @Inject constructor(
    private val jamendoApi: JamendoApiService,
    private val lrcLibApi: LrcLibApiService,
    private val trackDao: TrackDao,
    private val playlistDao: PlaylistDao,
    private val downloadDao: DownloadDao
) {

    private val clientId = Constants.JAMENDO_CLIENT_ID

    // ===== TRACKS =====

    suspend fun getTrendingTracks(limit: Int = 20, offset: Int = 0): Result<List<Track>> {
        return try {
            val response = jamendoApi.getTrendingTracks(
                clientId = clientId,
                limit = limit,
                offset = offset
            )
            Result.success(response.results.map { it.toTrack() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getNewReleases(limit: Int = 20): Result<List<Track>> {
        return try {
            val response = jamendoApi.getNewReleases(clientId = clientId, limit = limit)
            Result.success(response.results.map { it.toTrack() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchTracks(query: String, limit: Int = 20, offset: Int = 0): Result<List<Track>> {
        return try {
            val response = jamendoApi.searchTracks(
                clientId = clientId,
                query = query,
                limit = limit,
                offset = offset
            )
            Result.success(response.results.map { it.toTrack() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTracksByGenre(genre: String, limit: Int = 20): Result<List<Track>> {
        return try {
            val response = jamendoApi.getTracksByGenre(
                clientId = clientId,
                tags = genre.lowercase(),
                limit = limit
            )
            Result.success(response.results.map { it.toTrack() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAlbumTracks(albumId: String): Result<List<Track>> {
        return try {
            val response = jamendoApi.getAlbumTracks(clientId = clientId, albumId = albumId)
            Result.success(response.results.map { it.toTrack() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getArtistTracks(artistId: String): Result<List<Track>> {
        return try {
            val response = jamendoApi.getArtistTracks(clientId = clientId, artistId = artistId)
            Result.success(response.results.map { it.toTrack() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ===== ARTISTS =====

    suspend fun searchArtists(query: String): Result<List<Artist>> {
        return try {
            val response = jamendoApi.searchArtists(clientId = clientId, query = query)
            Result.success(response.results.map { it.toArtist() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getFeaturedArtists(): Result<List<Artist>> {
        return try {
            val response = jamendoApi.getFeaturedArtists(clientId = clientId)
            Result.success(response.results.map { it.toArtist() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ===== ALBUMS =====

    suspend fun searchAlbums(query: String): Result<List<Album>> {
        return try {
            val response = jamendoApi.searchAlbums(clientId = clientId, query = query)
            Result.success(response.results.map { it.toAlbum() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getNewAlbums(): Result<List<Album>> {
        return try {
            val response = jamendoApi.getNewAlbums(clientId = clientId)
            Result.success(response.results.map { it.toAlbum() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getArtistAlbums(artistId: String): Result<List<Album>> {
        return try {
            val response = jamendoApi.getArtistAlbums(clientId = clientId, artistId = artistId)
            Result.success(response.results.map { it.toAlbum() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ===== LOCAL FAVORITES =====

    fun getFavoriteTracks(): Flow<List<Track>> {
        return trackDao.getFavoriteTracks().map { entities ->
            entities.map { it.toTrack() }
        }
    }

    suspend fun toggleFavorite(track: Track) {
        val existing = trackDao.getTrackById(track.id)
        if (existing != null) {
            trackDao.updateFavoriteStatus(track.id, !existing.isFavorite)
        } else {
            trackDao.insertTrack(track.toEntity().copy(isFavorite = true))
        }
    }

    suspend fun isFavorite(trackId: String): Boolean {
        return trackDao.getTrackById(trackId)?.isFavorite ?: false
    }

    // ===== LOCAL PLAYLISTS =====

    fun getUserPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylists().map { entities ->
            entities.map { it.toPlaylist() }
        }
    }

    suspend fun createPlaylist(name: String, description: String = ""): Long {
        val entity = PlaylistEntity(name = name, description = description)
        return playlistDao.insertPlaylist(entity)
    }

    suspend fun deletePlaylist(playlistId: Long) {
        playlistDao.deletePlaylist(playlistId)
    }

    suspend fun addTrackToPlaylist(playlistId: Long, track: Track) {
        trackDao.insertTrack(track.toEntity())
        val count = playlistDao.getPlaylistTracks(playlistId)
        val entity = PlaylistTrackEntity(
            playlistId = playlistId,
            trackId = track.id,
            position = 0
        )
        playlistDao.addTrackToPlaylist(entity)
    }

    suspend fun removeTrackFromPlaylist(playlistId: Long, trackId: String) {
        playlistDao.removeTrackFromPlaylist(playlistId, trackId)
    }

    fun getPlaylistTracks(playlistId: Long): Flow<List<Track>> {
        return playlistDao.getPlaylistTracks(playlistId).map { entities ->
            entities.map { it.toTrack() }
        }
    }

    // ===== RECENTLY PLAYED =====

    fun getRecentlyPlayed(): Flow<List<Track>> {
        return trackDao.getRecentlyPlayed().map { entities ->
            entities.map { it.toTrack() }
        }
    }

    suspend fun addToRecentlyPlayed(track: Track) {
        trackDao.insertTrack(track.toEntity())
        trackDao.incrementPlayCount(track.id, System.currentTimeMillis())
        trackDao.insertRecentlyPlayed(RecentlyPlayedEntity(trackId = track.id))
    }

    // ===== DOWNLOADS =====

    fun getDownloadedTracks(): Flow<List<Track>> {
        return trackDao.getDownloadedTracks().map { entities ->
            entities.map { it.toTrack() }
        }
    }

    // ===== LYRICS =====

    suspend fun getLyrics(
        trackName: String,
        artistName: String,
        albumName: String = "",
        duration: Int = 0
    ): Result<Lyrics> {
        return try {
            val response = lrcLibApi.getLyrics(
                trackName = trackName,
                artistName = artistName,
                albumName = albumName,
                duration = duration
            )
            Result.success(Lyrics(
                trackName = response.trackName,
                artistName = response.artistName,
                albumName = response.albumName,
                plainLyrics = response.plainLyrics,
                syncedLyrics = response.syncedLyrics
            ))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// ===== MAPPER EXTENSIONS =====

fun com.salmandev.imusic.data.api.JamendoTrack.toTrack() = com.salmandev.imusic.data.model.Track(
    id = id,
    name = name,
    artistId = artistId,
    artistName = artistName,
    albumId = albumId,
    albumName = albumName,
    albumArtUrl = albumImage,
    audioUrl = audio,
    duration = duration,
    shareUrl = shareUrl,
    license = licenseUrl,
    genre = tags,
    releaseDate = releaseDate,
    downloadCount = downloadCount
)

fun com.salmandev.imusic.data.api.JamendoArtist.toArtist() = com.salmandev.imusic.data.model.Artist(
    id = id,
    name = name,
    imageUrl = image,
    website = website,
    joinDate = joinDate,
    country = country,
    shareUrl = shareUrl
)

fun com.salmandev.imusic.data.api.JamendoAlbum.toAlbum() = com.salmandev.imusic.data.model.Album(
    id = id,
    name = name,
    artistId = artistId,
    artistName = artistName,
    coverUrl = image,
    releaseDate = releaseDate,
    shareUrl = shareUrl
)

fun TrackEntity.toTrack() = com.salmandev.imusic.data.model.Track(
    id = id, name = name, artistId = artistId, artistName = artistName,
    albumId = albumId, albumName = albumName, albumArtUrl = albumArtUrl,
    audioUrl = if (isDownloaded && localPath.isNotEmpty()) localPath else audioUrl,
    duration = duration, shareUrl = shareUrl, license = license,
    genre = genre, releaseDate = releaseDate, isFavorite = isFavorite,
    isDownloaded = isDownloaded, localPath = localPath
)

fun com.salmandev.imusic.data.model.Track.toEntity() = TrackEntity(
    id = id, name = name, artistId = artistId, artistName = artistName,
    albumId = albumId, albumName = albumName, albumArtUrl = albumArtUrl,
    audioUrl = audioUrl, duration = duration, shareUrl = shareUrl,
    license = license, genre = genre, releaseDate = releaseDate,
    isFavorite = isFavorite, isDownloaded = isDownloaded, localPath = localPath
)

fun PlaylistEntity.toPlaylist() = com.salmandev.imusic.data.model.Playlist(
    id = id.toString(), name = name, description = description,
    coverUrl = coverUrl, createdAt = createdAt, isUserCreated = true
)
