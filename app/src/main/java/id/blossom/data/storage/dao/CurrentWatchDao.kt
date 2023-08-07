package id.blossom.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.blossom.data.storage.entity.CurrentWatchEntity

@Dao
interface CurrentWatchDao {



    @Query("SELECT * FROM current_watch_table WHERE animeId = :animeId")
    fun getCurrentWatchByAnimeId(animeId: String): List<CurrentWatchEntity>

    @Query("SELECT * FROM current_watch_table WHERE isAlreadyPlaying = 1")
    fun getCurrentWatch(): List<CurrentWatchEntity>

    @Query("SELECT * FROM current_watch_table WHERE isAlreadyPlaying = 1 AND animeId = :animeId GROUP BY animeId LIMIT 1")
    fun getCurrentWatchByAnimeIdAndIsAlreadyPlaying(animeId: String): CurrentWatchEntity

    @Query("SELECT * FROM current_watch_table WHERE isAlreadyPlaying = 1 AND episodeId = :episodeId LIMIT 1")
    fun getCurrentWatchByEpisodeIdAndIsAlreadyPlaying(episodeId: String): CurrentWatchEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWatch(currentWatchEntity: CurrentWatchEntity)

    @Query("UPDATE current_watch_table SET isAlreadyPlaying = :isAlreadyPlaying , duration = :duration, currentDuration = :currentDuration WHERE animeId = :animeId")
    fun updateCurrentWatchByAnimeId(isAlreadyPlaying: Boolean, duration: Long, currentDuration: Long, animeId: String)

    @Query("UPDATE current_watch_table SET isAlreadyPlaying = :isAlreadyPlaying , duration = :duration , currentDuration = :currentDuration WHERE episodeId = :episodeId")
    fun updateCurrentWatchByEpisodeId(isAlreadyPlaying: Boolean, duration: Long, currentDuration: Long, episodeId: String)

    // delete
    @Query("DELETE FROM current_watch_table WHERE animeId = :animeId")
    fun deleteCurrentWatchByAnimeId(animeId: String)

    @Query("DELETE FROM current_watch_table WHERE episodeId = :episodeId")
    fun deleteCurrentWatchByEpisodeId(episodeId: String)


}