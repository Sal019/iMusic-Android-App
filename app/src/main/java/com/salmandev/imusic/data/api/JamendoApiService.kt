package com.salmandev.imusic.data.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

// ===== JAMENDO API RESPONSE MODELS =====

data class JamendoTracksResponse(
    @SerializedName("results") val results: List<JamendoTrack> = emptyList(),
    @SerializedName("headers") val headers: JamendoHeaders? = null
)

data class JamendoHeaders(
    @SerializedName("status") val status: String = "",
    @SerializedName("code") val code: Int = 0,
    @SerializedName("error_message") val errorMessage: String = "",
    @SerializedName("results_count") val resultsCount: Int = 0,
    @SerializedName("next") val next: String = ""
)

data class JamendoTrack(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("duration") val duration: Int = 0,
    @SerializedName("artist_id") val artistId: String = "",
    @SerializedName("artist_name") val artistName: String = "",
    @SerializedName("album_id") val albumId: String = "",
    @SerializedName("album_name") val albumName: String = "",
    @SerializedName("album_image") val albumImage: String = "",
    @SerializedName("audio") val audio: String = "",
    @SerializedName("audiodownload") val audioDownload: String = "",
    @SerializedName("shareurl") val shareUrl: String = "",
    @SerializedName("license_ccurl") val licenseUrl: String = "",
    @SerializedName("tags") val tags: String = "",
    @SerializedName("releasedate") val releaseDate: String = "",
    @SerializedName("playlistcount") val playlistCount: Int = 0,
    @SerializedName("downloadcount") val downloadCount: Long = 0L
)

data class JamendoArtistsResponse(
    @SerializedName("results") val results: List<JamendoArtist> = emptyList()
)

data class JamendoArtist(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("website") val website: String = "",
    @SerializedName("joindate") val joinDate: String = "",
    @SerializedName("country") val country: String = "",
    @SerializedName("shareurl") val shareUrl: String = ""
)

data class JamendoAlbumsResponse(
    @SerializedName("results") val results: List<JamendoAlbum> = emptyList()
)

data class JamendoAlbum(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("artist_id") val artistId: String = "",
    @SerializedName("artist_name") val artistName: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("releasedate") val releaseDate: String = "",
    @SerializedName("zip") val zip: String = "",
    @SerializedName("shareurl") val shareUrl: String = ""
)

data class JamendoPlaylistsResponse(
    @SerializedName("results") val results: List<JamendoPlaylist> = emptyList()
)

data class JamendoPlaylist(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("creationdate") val creationDate: String = "",
    @SerializedName("user_name") val userName: String = "",
    @SerializedName("shareurl") val shareUrl: String = ""
)

// ===== JAMENDO API SERVICE =====

interface JamendoApiService {

    @GET("tracks/")
    suspend fun getTrendingTracks(
        @Query("client_id") clientId: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("order") order: String = "popularity_week",
        @Query("imagesize") imageSize: String = "300",
        @Query("include") include: String = "musicinfo+licenses"
    ): JamendoTracksResponse

    @GET("tracks/")
    suspend fun searchTracks(
        @Query("client_id") clientId: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("search") query: String,
        @Query("imagesize") imageSize: String = "300",
        @Query("include") include: String = "musicinfo"
    ): JamendoTracksResponse

    @GET("tracks/")
    suspend fun getTracksByGenre(
        @Query("client_id") clientId: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("tags") tags: String,
        @Query("order") order: String = "popularity_week",
        @Query("imagesize") imageSize: String = "300"
    ): JamendoTracksResponse

    @GET("tracks/")
    suspend fun getNewReleases(
        @Query("client_id") clientId: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("order") order: String = "releasedate_desc",
        @Query("imagesize") imageSize: String = "300"
    ): JamendoTracksResponse

    @GET("tracks/")
    suspend fun getAlbumTracks(
        @Query("client_id") clientId: String,
        @Query("format") format: String = "json",
        @Query("album_id") albumId: String,
        @Query("limit") limit: Int = 50,
        @Query("imagesize") imageSize: String = "300"
    ): JamendoTracksResponse

    @GET("tracks/")
    suspend fun getArtistTracks(
        @Query("client_id") clientId: String,
        @Query("format") format: String = "json",
        @Query("artist_id") artistId: String,
        @Query("limit") limit: Int = 30,
        @Query("imagesize") imageSize: String = "300",
        @Query("order") order: String = "popularity_total"
    ): JamendoTracksResponse

    @GET("artists/")
    suspend fun searchArtists(
        @Query("client_id") clientId: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("search") query: String,
        @Query("imagesize") imageSize: String = "300"
    ): JamendoArtistsResponse

    @GET("artists/")
    suspend fun getFeaturedArtists(
        @Query("client_id") clientId: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 10,
        @Query("order") order: String = "popularity_week",
        @Query("imagesize") imageSize: String = "300"
    ): JamendoArtistsResponse

    @GET("albums/")
    suspend fun searchAlbums(
        @Query("client_id") clientId: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("search") query: String,
        @Query("imagesize") imageSize: String = "300"
    ): JamendoAlbumsResponse

    @GET("albums/")
    suspend fun getNewAlbums(
        @Query("client_id") clientId: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 10,
        @Query("order") order: String = "releasedate_desc",
        @Query("imagesize") imageSize: String = "300"
    ): JamendoAlbumsResponse

    @GET("albums/")
    suspend fun getArtistAlbums(
        @Query("client_id") clientId: String,
        @Query("format") format: String = "json",
        @Query("artist_id") artistId: String,
        @Query("limit") limit: Int = 20,
        @Query("imagesize") imageSize: String = "300"
    ): JamendoAlbumsResponse
}
