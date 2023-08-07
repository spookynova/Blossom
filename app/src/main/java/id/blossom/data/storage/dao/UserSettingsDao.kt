package id.blossom.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.blossom.data.storage.entity.UserSettingsEntity

@Dao
interface UserSettingsDao {

    @Query("SELECT * FROM user_settings")
    fun getUserSettings(): UserSettingsEntity


    @Query("UPDATE user_settings SET isDarkMode = :isDarkMode")
    fun updateDarkMode(isDarkMode: Boolean)

    @Query("UPDATE user_settings SET vidQuality = :vidQuality")
    fun updateVidQuality(vidQuality: String)

    @Query("UPDATE user_settings SET isDevMode = :isDevMode")
    fun updateDevMode(isDevMode: Boolean)

    @Insert
    fun insertUserSettings(userSettingsEntity: UserSettingsEntity)

}