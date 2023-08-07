package id.blossom.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "user_settings")
data class UserSettingsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var isDarkMode: Boolean,
    var vidQuality : String,
    var isDevMode : Boolean
)
