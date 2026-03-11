package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui.FavoritesViewModel
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui.RandomCocktailViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheGreatestCocktailAppTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()
                val vm: RandomCocktailViewModel = viewModel()
                val favoritesVm: FavoritesViewModel = viewModel()
                val randomState = vm.state.collectAsState().value
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TGTopAppBar(
                            snackbarHostState = snackbarHostState,
                            navController = navController,
                            onRandom = { vm.loadRandom() },
                            currentDrinkId = randomState.drink?.idDrink,
                            currentDrinkName = randomState.drink?.strDrink,
                            currentDrinkThumb = randomState.drink?.strDrinkThumb,
                            favoritesVm = favoritesVm
                        )
                    },
                    bottomBar = { TGBottomBar(navController) },
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Random.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Random.route) {
                            DetailCocktailScreen(
                                modifier = Modifier.fillMaxSize(),
                                vm = vm
                            )
                        }
                        composable(Screen.Categories.route) {
                            CategoriesScreen(modifier = Modifier.fillMaxSize())
                        }
                        composable(Screen.Favorites.route) {
                            FavoritesScreen(modifier = Modifier.fillMaxSize())
                        }
                    }
                }

                        }
            }
        }





    }








@Composable
fun Chip(text: String, bg: Color) {
    Surface(
        color = bg.copy(alpha = 0.55f),
        shape = RoundedCornerShape(50)
    ) {
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            fontSize = 12.sp
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TGTopAppBar(
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    onRandom: () -> Unit,
    currentDrinkId: String?,
    currentDrinkName: String?,
    currentDrinkThumb: String?,
    favoritesVm: FavoritesViewModel
) {
    val scope = rememberCoroutineScope()
    var isFav by rememberSaveable { mutableStateOf(false) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route

    val title = when (route) {
        Screen.Random.route -> "Random"
        Screen.Categories.route -> "Catégories"
        Screen.Favorites.route -> "Favoris"
        else -> ""
    }

    LaunchedEffect(currentDrinkId) {
        if (currentDrinkId != null) {
            favoritesVm.isFavorite(currentDrinkId) { result ->
                isFav = result
            }
        } else {
            isFav = false
        }
    }

    CenterAlignedTopAppBar(
        title = { Text(title) },
        actions = {
            if (route == Screen.Random.route) {
                IconButton(onClick = {
                    onRandom()
                    scope.launch {
                        snackbarHostState.showSnackbar("Nouveau cocktail ")
                    }
                }) {
                    Icon(Icons.Default.Refresh, contentDescription = "Random")
                }

                IconToggleButton(
                    checked = isFav,
                    onCheckedChange = { checked ->
                        val id = currentDrinkId
                        val name = currentDrinkName
                        val thumb = currentDrinkThumb

                        if (id != null && name != null) {
                            isFav = checked

                            if (checked) {
                                favoritesVm.addFavorite(id, name, thumb)
                            } else {
                                favoritesVm.removeFavorite(id)
                            }

                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    if (checked) "Ajouté aux favoris"
                                    else "Retiré des favoris"
                                )
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favori"
                    )
                }
            }
        }
    )
}
@Composable
fun IngredientsCard(
    ingredients: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White.copy(alpha = 0.12f),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Ingrédients",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(12.dp))

            ingredients.forEach { (name, quantity) ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "• $name",
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Text(
                        text = quantity,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}



@Composable
fun TGBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        bottomTabs.forEach { screen ->
            val icon = when (screen) {
                Screen.Random -> Icons.Filled.Home
                Screen.Categories -> Icons.AutoMirrored.Filled.List
                Screen.Favorites -> Icons.Filled.Favorite
            }

            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(icon, contentDescription = screen.label) },
                label = { Text(screen.label) }
            )
        }
    }
}

sealed class Screen(val route: String, val label: String) {
    data object Random : Screen("random", "Random")
    data object Categories : Screen("categories", "Catégories")
    data object Favorites : Screen("favorites", "Favoris")
}

val bottomTabs = listOf(Screen.Random, Screen.Categories, Screen.Favorites)
