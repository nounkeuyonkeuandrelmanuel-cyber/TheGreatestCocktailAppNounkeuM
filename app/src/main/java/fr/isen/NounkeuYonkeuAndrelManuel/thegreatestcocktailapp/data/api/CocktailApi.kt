package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.api
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.model.CategoriesResponse
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.model.DrinksResponse
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.model.DrinksByCategoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("random.php")
    suspend fun getRandomCocktail(): DrinksResponse

    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") id: String): DrinksResponse

    @GET("list.php")
    suspend fun getCategories(@Query("c") type: String = "list"): CategoriesResponse

    @GET("filter.php")
    suspend fun getDrinksByCategory(@Query("c") category: String): DrinksByCategoryResponse


}
