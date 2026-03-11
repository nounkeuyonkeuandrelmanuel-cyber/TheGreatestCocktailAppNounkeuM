
package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.CocktailRepository
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.model.DrinkItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DrinksListUiState(
    val isLoading: Boolean = false,
    val drinks: List<DrinkItem> = emptyList(),
    val error: String? = null
)

class DrinksListViewModel(
    private val repo: CocktailRepository = CocktailRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(DrinksListUiState(isLoading = false))
    val state: StateFlow<DrinksListUiState> = _state

    fun load(category: String) {
        viewModelScope.launch {
            _state.value = DrinksListUiState(isLoading = true)
            try {
                val list = repo.drinksByCategory(category)
                _state.value = DrinksListUiState(isLoading = false, drinks = list)
            } catch (e: Exception) {
                _state.value = DrinksListUiState(
                    isLoading = false,
                    error = e.message ?: "Erreur réseau"
                )
            }
        }
    }
}