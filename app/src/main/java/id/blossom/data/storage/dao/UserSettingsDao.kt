package id.blossom.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.blossom.data.storage.entity.UserSettingsEntity

@Dao
interface UserSettingsDao {

    @Query("SELECT * FROM user_settings")
    fun getUserSettings(): UserSettingsEntity

    @Query("UPDATE user_settings SET isDarkMode = :isDarkMode, vidQuality = :vidQuality, isDevMode = :isDevMode WHERE id = 1")
    fun updateUserSettings(isDarkMode: Boolean, vidQuality: String, isDevMode: Boolean)
    @Insert
    fun insertUserSettings(userSettingsEntity: UserSettingsEntity)

}