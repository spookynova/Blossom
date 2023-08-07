package id.blossom.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "current_watch_table")
data class CurrentWatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val animeId: String,
    val episodeId: String,
    val duration: Long,
    val currentDuration: Long,
    val isAlreadyPlaying: Boolean
)
