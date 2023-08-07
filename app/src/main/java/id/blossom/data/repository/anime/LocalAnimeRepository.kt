package id.blossom.data.repository.anime

import id.blossom.data.api.NetworkService
import id.blossom.data.storage.dao.CurrentWatchDao
import id.blossom.data.storage.dao.FavoriteDao
import id.blossom.data.storage.dao.UserSettingsDao
import id.blossom.data.storage.entity.CurrentWatchEntity
import id.blossom.data.storage.entity.FavoriteEntity
import id.blossom.data.storage.entity.UserSettingsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalAnimeRepository @Inject constructor(private val favoriteDao: FavoriteDao, private val userSettingsDao: UserSettingsDao, private val currentWatchDao: CurrentWatchDao) {
    suspend fun getFavoriteAnime() : List<FavoriteEntity> {
        return withContext(Dispatchers.IO) {
            favoriteDao.getFavorite()
        }
    }

    suspend fun getFavoriteAnimeByAnimeId(id: String): FavoriteEntity {
        return withContext(Dispatchers.IO) {
            favoriteDao.getFavoriteByAnimeId(id)
        }
    }

    fun insertFavoriteAnime(data : FavoriteEntity) {
        GlobalScope.launch {
            favoriteDao.insertFavorite(data)
        }
    }

    fun deleteFavoriteAnime(data : FavoriteEntity) {
        GlobalScope.launch {
            favoriteDao.deleteFavorite(data)
        }
    }

    fun deleteFavoriteAnimeByAnimeId(id : String) {
        GlobalScope.launch {
            favoriteDao.deleteFavoriteByAnimeId(id)
        }
    }

    // ------------------ User Settings ------------------

    suspend fun getUserSettings(): UserSettingsEntity {
        return withContext(Dispatchers.IO) {
            userSettingsDao.getUserSettings()
        }
    }

    fun updateDarkMode(isDarkMode: Boolean) {
        GlobalScope.launch {
            userSettingsDao.updateDarkMode(isDarkMode)
        }
    }

    fun updateVidQuality(vidQuality: String) {
        GlobalScope.launch {
            userSettingsDao.updateVidQuality(vidQuality)
        }
    }

    fun updateDevMode(isDevMode: Boolean) {
        GlobalScope.launch {
            userSettingsDao.updateDevMode(isDevMode)
        }
    }

    fun insertUserSettings(userSettingsEntity: UserSettingsEntity) {
        GlobalScope.launch {
            userSettingsDao.insertUserSettings(userSettingsEntity)
        }
    }

    // ------------------ Current Watch ------------------

    suspend fun getAllCurrentWatchByAnimeId(animeId: String): List<CurrentWatchEntity> {
        return withContext(Dispatchers.IO) {
            currentWatchDao.getCurrentWatchByAnimeId(animeId)
        }
    }

    suspend fun getCurrentWatchByAnimeId(id: String): CurrentWatchEntity {
        return withContext(Dispatchers.IO) {
            currentWatchDao.getCurrentWatchByAnimeIdAndIsAlreadyPlaying(id)
        }
    }

    suspend fun getCurrentWatchByEpisodeId(id: String): CurrentWatchEntity {
        return withContext(Dispatchers.IO) {
            currentWatchDao.getCurrentWatchByEpisodeIdAndIsAlreadyPlaying(id)
        }
    }

    fun insertCurrentWatch(currentWatchEntity: CurrentWatchEntity) {
        GlobalScope.launch {
            currentWatchDao.insertCurrentWatch(currentWatchEntity)
        }
    }

    fun deleteCurrentWatchByAnimeId(id: String) {
        GlobalScope.launch {
            currentWatchDao.deleteCurrentWatchByAnimeId(id)
        }
    }

    fun deleteCurrentWatchByEpisodeId(id: String) {
        GlobalScope.launch {
            currentWatchDao.deleteCurrentWatchByEpisodeId(id)
        }
    }


    fun updateCurrentWatchByAnimeId(isAlreadyPlaying : Boolean, duration : Long , currentDuration : Long, animeId: String ) {
        GlobalScope.launch {
            currentWatchDao.updateCurrentWatchByAnimeId(isAlreadyPlaying, duration, currentDuration, animeId)
        }
    }

    fun updateCurrentWatchByEpisodeId(isAlreadyPlaying : Boolean, duration : Long , currentDuration : Long, episodeId: String ) {
        GlobalScope.launch {
            currentWatchDao.updateCurrentWatchByEpisodeId(isAlreadyPlaying, duration, currentDuration, episodeId)
        }
    }


}