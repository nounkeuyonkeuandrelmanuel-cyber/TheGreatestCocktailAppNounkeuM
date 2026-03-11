package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data

import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.api.RetrofitClient
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.model.Drink
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.model.DrinkItem

class CocktailRepository {
    private val api = RetrofitClient.api

    suspend fun random(): Drink? = api.getRandomCocktail().drinks?.firstOrNull()
    suspend fun byId(id: String): Drink? = api.getCocktailById(id).drinks?.firstOrNull()

    suspend fun categories(): List<String> {
        val items = api.getCategories().drinks.orEmpty()
        return items.mapNotNull { it.strCategory?.trim() }
    }

    suspend fun drinksByCategory(category: String): List<DrinkItem> {
        return api.getDrinksByCategory(category).drinks.orEmpty()
    }
}
