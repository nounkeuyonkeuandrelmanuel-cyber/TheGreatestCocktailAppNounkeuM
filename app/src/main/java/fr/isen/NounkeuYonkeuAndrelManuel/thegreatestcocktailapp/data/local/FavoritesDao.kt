package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites ORDER BY name ASC")
    fun observeAll(): Flow<List<FavoriteCocktailEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE idDrink = :id)")
    suspend fun isFavorite(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(fav: FavoriteCocktailEntity)

    @Query("DELETE FROM favorites WHERE idDrink = :id")
    suspend fun remove(id: String)
}