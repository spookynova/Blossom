package id.blossom.data.storage.dao

import androidx.room.*
import id.blossom.data.storage.entity.FavoriteEntity

@Dao
interface FavoriteDao {


    @Query("SELECT * FROM favorite_table")
    fun getAllFavorite(): List<FavoriteEntity>

    @Query("SELECT * FROM favorite_table WHERE animeId = :animeId")
    fun getFavoriteByAnimeId(animeId: String): FavoriteEntity

    @Query("SELECT * FROM favorite_table WHERE isFavorite = 1")
    fun getFavorite(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("UPDATE favorite_table SET isFavorite = :isFavorite WHERE animeId = :animeId")
    fun updateFavorite(isFavorite: Boolean, animeId: String)

    @Delete
    fun deleteFavorite(favoriteEntity: FavoriteEntity)


    @Query("DELETE FROM favorite_table WHERE animeId = :animeId")
    fun deleteFavoriteByAnimeId(animeId: String)
}