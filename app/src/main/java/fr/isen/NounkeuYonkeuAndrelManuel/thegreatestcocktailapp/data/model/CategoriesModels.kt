package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.model

data class CategoriesResponse(
    val drinks: List<CategoryItem>?
)

data class CategoryItem(
    val strCategory: String?
)