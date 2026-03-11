package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui.DrinksListViewModel

@Composable
fun DrinksListScreen(category: String, modifier: Modifier = Modifier) {
    val vm: DrinksListViewModel = viewModel()
    val state = vm.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(category) {
        vm.load(category)
    }

    when {
        state.isLoading -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Column(modifier.padding(16.dp)) {
                Text("Erreur: ${state.error}")
                Spacer(Modifier.height(8.dp))
                Button(onClick = { vm.load(category) }) { Text("Réessayer") }
            }
        }

        else -> {
            LazyColumn(modifier = modifier.padding(16.dp)) {
                item {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.height(12.dp))
                }

                items(state.drinks) { drink ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable {
                                val id = drink.idDrink ?: return@clickable
                                val intent = Intent(context, CocktailDetailActivity::class.java)
                                intent.putExtra("drink_id", id)
                                context.startActivity(intent)
                            }
                    ) {
                        Row(modifier = Modifier.padding(12.dp)) {
                            AsyncImage(
                                model = drink.strDrinkThumb,
                                contentDescription = drink.strDrink,
                                modifier = Modifier.size(64.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = drink.strDrink ?: "Sans nom",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "ID: ${drink.idDrink ?: "-"}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}