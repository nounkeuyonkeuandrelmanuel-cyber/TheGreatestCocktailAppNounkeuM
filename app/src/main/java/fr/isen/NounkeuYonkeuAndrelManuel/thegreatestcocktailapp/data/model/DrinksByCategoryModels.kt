package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.model

data class DrinksByCategoryResponse(
    val drinks: List<DrinkItem>?
)

data class DrinkItem(
    val idDrink: String?,
    val strDrink: String?,
    val strDrinkThumb: String?
)