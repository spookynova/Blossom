package id.blossom.ui.fragment.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.blossom.data.model.anime.genres.GenresAnimeDataItem
import id.blossom.data.model.anime.search.SearchAnimeDataItem
import id.blossom.data.repository.anime.AnimeRepository
import id.blossom.ui.base.UiState
import id.blossom.utils.AppConstant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchViewModel (private val animeRepository: AnimeRepository) : ViewModel() {
    private val _uiStateSearchAnime = MutableStateFlow<UiState<List<SearchAnimeDataItem>>>(UiState.Loading)
    val uiStateSearchAnime: StateFlow<UiState<List<SearchAnimeDataItem>>> = _uiStateSearchAnime


    init {

    }

    fun fetchSearchAnime(query : String) {
        viewModelScope.launch {
            animeRepository.getSearchAnime(query)
                .catch { e ->
                    _uiStateSearchAnime.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiStateSearchAnime.value = UiState.Success(it)
                }
        }
    }
}