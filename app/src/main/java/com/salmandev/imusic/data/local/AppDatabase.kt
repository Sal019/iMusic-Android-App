package com.salmandev.imusic.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.salmandev.imusic.data.local.dao.DownloadDao
import com.salmandev.imusic.data.local.dao.PlaylistDao
import com.salmandev.imusic.data.local.dao.TrackDao
import com.salmandev.imusic.data.local.entity.*

@Database(
    entities = [
        TrackEntity::class,
        PlaylistEntity::class,
        PlaylistTrackEntity::class,
        RecentlyPlayedEntity::class,
        DownloadEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
    abstract fun playlistDao(): PlaylistDao
    abstract fun downloadDao(): DownloadDao

    companion object {
        const val DATABASE_NAME = "imusic_db"
    }
}
