package id.blossom.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import id.blossom.data.storage.dao.CurrentWatchDao
import id.blossom.data.storage.dao.FavoriteDao
import id.blossom.data.storage.dao.UserSettingsDao
import id.blossom.data.storage.entity.CurrentWatchEntity
import id.blossom.data.storage.entity.FavoriteEntity
import id.blossom.data.storage.entity.UserSettingsEntity

@Database(entities = [FavoriteEntity::class, UserSettingsEntity::class, CurrentWatchEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDao
    abstract fun userSettingsDao(): UserSettingsDao
    abstract fun currentWatchDao(): CurrentWatchDao
}