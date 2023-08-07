package id.blossom.data.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val animeId: String,
    val title: String,
    val image: String,
    var isFavorite: Boolean
)
