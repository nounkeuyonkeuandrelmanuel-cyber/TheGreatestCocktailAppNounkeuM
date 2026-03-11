
package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteCocktailEntity(
    @PrimaryKey val idDrink: String,
    val name: String,
    val thumb: String?
)














