package fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.FavoritesRepository
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.local.DbProvider
import fr.isen.NounkeuYonkeuAndrelManuel.thegreatestcocktailapp.data.local.FavoriteCocktailEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class FavoritesUiState(
    val isLoading: Boolean = true,
    val favorites: List<FavoriteCocktailEntity> = emptyList(),
    val error: String? = null
)

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = FavoritesRepository(
        DbProvider.get(application).favoritesDao()
    )

    private val _state = MutableStateFlow(FavoritesUiState())
    val state: StateFlow<FavoritesUiState> = _state

    init {
        viewModelScope.launch {
            try {
                repo.observeAll().collect { list ->
                    _state.value = FavoritesUiState(
                        isLoading = false,
                        favorites = list
                    )
                }
            } catch (e: Exception) {
                _state.value = FavoritesUiState(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun addFavorite(id: String, name: String, thumb: String?) {
        viewModelScope.launch {
            repo.add(id, name, thumb)
        }
    }

    fun removeFavorite(id: String) {
        viewModelScope.launch {
            repo.remove(id)
        }
    }

    fun isFavorite(id: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            onResult(repo.isFavorite(id))
        }
    }
}