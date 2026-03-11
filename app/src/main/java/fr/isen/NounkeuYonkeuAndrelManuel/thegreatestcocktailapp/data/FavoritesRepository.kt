
package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data

import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.local.FavoriteCocktailEntity
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.local.FavoritesDao
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(private val dao: FavoritesDao) {

    fun observeAll(): Flow<List<FavoriteCocktailEntity>> = dao.observeAll()

    suspend fun isFavorite(id: String): Boolean = dao.isFavorite(id)

    suspend fun add(id: String, name: String, thumb: String?) {
        dao.add(FavoriteCocktailEntity(idDrink = id, name = name, thumb = thumb))
    }

    suspend fun remove(id: String) {
        dao.remove(id)
    }
}