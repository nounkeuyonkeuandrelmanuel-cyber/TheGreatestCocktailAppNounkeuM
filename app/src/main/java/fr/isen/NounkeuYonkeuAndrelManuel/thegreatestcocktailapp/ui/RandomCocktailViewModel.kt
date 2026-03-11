package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.CocktailRepository
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.model.Drink
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RandomCocktailUiState(
    val isLoading: Boolean = false,
    val drink: Drink? = null,
    val error: String? = null
)

class RandomCocktailViewModel(
    private val repo: CocktailRepository = CocktailRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(RandomCocktailUiState(isLoading = true))
    val state: StateFlow<RandomCocktailUiState> = _state

    init {
        loadRandom()
    }

    fun loadRandom() {
        viewModelScope.launch {
            _state.value = RandomCocktailUiState(isLoading = true)
            try {
                val drink = repo.random()
                _state.value = RandomCocktailUiState(isLoading = false, drink = drink)
            } catch (e: Exception) {
                _state.value = RandomCocktailUiState(
                    isLoading = false,
                    error = e.message ?: "Erreur réseau"
                )
            }
        }
    }

    fun loadById(id: String) {
        viewModelScope.launch {
            _state.value = RandomCocktailUiState(isLoading = true)
            try {
                val drink = repo.byId(id)
                _state.value = RandomCocktailUiState(isLoading = false, drink = drink)
            } catch (e: Exception) {
                _state.value = RandomCocktailUiState(
                    isLoading = false,
                    error = e.message ?: "Erreur réseau"
                )
            }
        }
    }
}