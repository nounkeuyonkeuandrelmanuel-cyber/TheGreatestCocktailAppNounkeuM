package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class DrinksListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val category = intent.getStringExtra("category_name") ?: "Ordinary Drink"

        setContent {
            TheGreatestCocktailAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DrinksListScreen(category = category)
                }
            }
        }
    }
}