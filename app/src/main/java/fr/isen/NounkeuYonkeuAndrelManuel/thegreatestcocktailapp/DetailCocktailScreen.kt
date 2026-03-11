package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import coil.compose.AsyncImage
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui.RandomCocktailViewModel
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.model.toIngredientsList


@Composable
fun DetailCocktailScreen(modifier: Modifier = Modifier,vm: RandomCocktailViewModel) {

    val state = vm.state.collectAsState().value

    val bg = Brush.verticalGradient(
        colors = listOf(Color(0xFFFF6A00), Color(0xFFFFC107))
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bg)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        when {
            state.isLoading -> {
                Text("Chargement...", color = Color.White)
            }

            state.error != null -> {
                Text("Erreur: ${state.error}", color = Color.Red)
            }

            state.drink != null -> {
                val d = state.drink

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .size(220.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White.copy(alpha = 0.18f), CircleShape)
                            .background(Color.White.copy(alpha = 0.10f)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = d.strDrinkThumb,
                            contentDescription = d.strDrink ?: "Cocktail",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = d.strDrink ?: "Sans nom",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Chip(text = d.strCategory ?: "Unknown", bg = Color(0xFF3A6EA5))
                        Chip(text = d.strAlcoholic ?: "Unknown", bg = Color(0xFF2E8B57))
                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = d.strGlass ?: "",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 13.sp
                    )

                    Spacer(Modifier.height(24.dp))

                    val ingredients = d.toIngredientsList()

                    IngredientsCard(
                        ingredients = ingredients
                    )
                    Spacer(Modifier.height(14.dp))

                    RecipeCard(
                        text = d.strInstructions ?: "Aucune instruction disponible"
                    )


                }
            }
        }
    }
}


@Composable
fun RecipeCard(text: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White.copy(alpha = 0.12f),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Recette",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = text,
                color = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}
