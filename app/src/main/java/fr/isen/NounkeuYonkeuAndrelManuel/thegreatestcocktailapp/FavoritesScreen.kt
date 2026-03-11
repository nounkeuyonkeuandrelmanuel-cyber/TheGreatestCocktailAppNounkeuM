package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui.FavoritesViewModel

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {

    val vm: FavoritesViewModel = viewModel()
    val state by vm.state.collectAsState()
    val context = LocalContext.current

    if (state.isLoading) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (state.favorites.isEmpty()) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Aucun favori")
        }
        return
    }

    LazyColumn(modifier = modifier.padding(16.dp)) {

        items(state.favorites) { fav ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    AsyncImage(
                        model = fav.thumb,
                        contentDescription = fav.name,
                        modifier = Modifier
                            .size(64.dp)
                            .clickable {
                                val intent = Intent(context, CocktailDetailActivity::class.java)
                                intent.putExtra("drink_id", fav.idDrink)
                                context.startActivity(intent)
                            },
                        contentScale = ContentScale.Crop
                    )

                    Spacer(Modifier.width(12.dp))

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                val intent = Intent(context, CocktailDetailActivity::class.java)
                                intent.putExtra("drink_id", fav.idDrink)
                                context.startActivity(intent)
                            }
                    ) {
                        Text(
                            text = fav.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    IconButton(
                        onClick = {
                            vm.removeFavorite(fav.idDrink)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Supprimer "
                        )
                    }
                }
            }
        }
    }
}