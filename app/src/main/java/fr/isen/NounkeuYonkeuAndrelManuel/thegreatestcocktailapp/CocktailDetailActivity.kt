package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui.RandomCocktailViewModel
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class CocktailDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TheGreatestCocktailAppTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val vm: RandomCocktailViewModel = viewModel()

                val drinkId = intent.getStringExtra("drink_id")

                LaunchedEffect(drinkId) {
                    if (drinkId != null) vm.loadById(drinkId)
                    else vm.loadRandom() // fallback
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    DetailCocktailScreen(
                        modifier = Modifier.padding(innerPadding),
                        vm = vm
                    )
                }
            }
        }
    }
}