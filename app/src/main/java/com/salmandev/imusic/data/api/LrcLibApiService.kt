package com.salmandev.imusic.data.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

// ===== LRCLIB RESPONSE MODELS =====

data class LrcLibResponse(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("trackName") val trackName: String = "",
    @SerializedName("artistName") val artistName: String = "",
    @SerializedName("albumName") val albumName: String = "",
    @SerializedName("duration") val duration: Double = 0.0,
    @SerializedName("instrumental") val instrumental: Boolean = false,
    @SerializedName("plainLyrics") val plainLyrics: String = "",
    @SerializedName("syncedLyrics") val syncedLyrics: String = ""
)

// ===== LRCLIB API SERVICE =====

interface LrcLibApiService {

    @GET("get")
    suspend fun getLyrics(
        @Query("track_name") trackName: String,
        @Query("artist_name") artistName: String,
        @Query("album_name") albumName: String = "",
        @Query("duration") duration: Int = 0
    ): LrcLibResponse

    @GET("search")
    suspend fun searchLyrics(
        @Query("track_name") trackName: String,
        @Query("artist_name") artistName: String = ""
    ): List<LrcLibResponse>
}
