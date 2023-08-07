package id.blossom.ui.activity.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.blossom.data.model.anime.genres.result.PropertyGenresAnimeDataItem
import id.blossom.data.model.anime.recent.RecentAnimeData
import id.blossom.data.repository.anime.AnimeRepository
import id.blossom.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class GenresResultViewModel (private val animeRepository: AnimeRepository) : ViewModel() {
    private val _uiStateGenresResultAnime =
        MutableStateFlow<UiState<List<PropertyGenresAnimeDataItem>>>(UiState.Loading)
    val uiStateGenresResultAnime: StateFlow<UiState<List<PropertyGenresAnimeDataItem>>> = _uiStateGenresResultAnime


    init {

    }

    fun fetchGenresResultAnime(genres : String) {
        viewModelScope.launch {
            animeRepository.getResultGenresAnime(genres)
                .catch { e ->
                    _uiStateGenresResultAnime.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiStateGenresResultAnime.value = UiState.Success(it)
                }
        }
    }
}