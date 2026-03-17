package com.salmandev.imusic.core

object Constants {
    // Jamendo API
    const val JAMENDO_BASE_URL = "https://api.jamendo.com/v3.0/"
    const val JAMENDO_CLIENT_ID = "b6747d04"
    const val JAMENDO_FORMAT = "json"
    const val JAMENDO_IMAGE_SIZE = "300x300"

    // LrcLib API (Lyrics)
    const val LRCLIB_BASE_URL = "https://lrclib.net/api/"

    // MusicBrainz API
    const val MUSICBRAINZ_BASE_URL = "https://musicbrainz.org/ws/2/"

    // Pagination
    const val DEFAULT_PAGE_SIZE = 20
    const val FEATURED_LIMIT = 10
    const val TRENDING_LIMIT = 20

    // MediaSession
    const val MEDIA_SESSION_TAG = "iMusic_Session"

    // Notification Channel
    const val NOTIFICATION_CHANNEL_ID = "imusic_playback_channel"
    const val NOTIFICATION_CHANNEL_NAME = "iMusic Playback"
    const val NOTIFICATION_ID = 101

    // Download channel
    const val DOWNLOAD_CHANNEL_ID = "imusic_download_channel"
    const val DOWNLOAD_CHANNEL_NAME = "iMusic Downloads"
    const val DOWNLOAD_NOTIFICATION_ID = 102

    // DataStore keys
    const val PREFS_THEME_KEY = "theme_mode"
    const val PREFS_EQUALIZER_PRESET = "equalizer_preset"
    const val PREFS_SLEEP_TIMER = "sleep_timer"
    const val PREFS_AUDIO_QUALITY = "audio_quality"
    const val PREFS_CROSS_FADE = "cross_fade_duration"
    const val PREFS_LANGUAGE = "language"

    // Default values
    const val DEFAULT_CROSSFADE_DURATION = 3000L // ms
    const val DEFAULT_SLEEP_TIMER = 0L // 0 = disabled
    const val MAX_SLEEP_TIMER = 90 // minutes

    // Equalizer bands (Hz)
    val EQUALIZER_BANDS = listOf(60, 230, 910, 3600, 14000)

    // Genre list
    val MUSIC_GENRES = listOf(
        "Pop", "Rock", "Electronic", "Hip-Hop", "Jazz",
        "Classical", "Country", "R&B", "Metal", "Folk",
        "Reggae", "Latin", "Blues", "Soul", "Ambient",
        "Dance", "Indie", "Alternative", "Punk", "Acoustic"
    )

    // Sleep timer options (minutes, 0 = off)
    val SLEEP_TIMER_OPTIONS = listOf(0, 5, 10, 15, 20, 30, 45, 60, 90)

    // Audio quality options
    val AUDIO_QUALITY_OPTIONS = listOf("Low (64kbps)", "Normal (128kbps)", "High (256kbps)", "Very High (320kbps)")

    // Featured playlist names
    val FEATURED_PLAYLIST_NAMES = listOf(
        "🔥 Trending Now",
        "💚 Today's Hits",
        "🎵 Chill Vibes",
        "⚡ Energy Boost",
        "🌙 Late Night",
        "☀️ Morning Mix",
        "🏃 Workout",
        "❤️ Love Songs"
    )
}
