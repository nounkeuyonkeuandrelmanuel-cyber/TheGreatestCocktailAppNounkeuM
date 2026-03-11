
package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.CocktailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CategoriesUiState(
    val isLoading: Boolean = false,
    val categories: List<String> = emptyList(),
    val error: String? = null
)

class CategoriesViewModel(
    private val repo: CocktailRepository = CocktailRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(CategoriesUiState(isLoading = true))
    val state: StateFlow<CategoriesUiState> = _state

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _state.value = CategoriesUiState(isLoading = true)
            try {
                val cats = repo.categories()
                _state.value = CategoriesUiState(isLoading = false, categories = cats)
            } catch (e: Exception) {
                _state.value = CategoriesUiState(
                    isLoading = false,
                    error = e.message ?: "Erreur réseau"
                )
            }
        }
    }
}