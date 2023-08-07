package id.blossom.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.blossom.data.model.anime.genres.GenresAnimeDataItem
import id.blossom.data.model.anime.genres.GenresAnimeResponse
import id.blossom.data.model.anime.ongoing.OngoingAnimeDataItem
import id.blossom.data.model.anime.recent.RecentAnimeData
import id.blossom.data.repository.anime.AnimeRepository
import id.blossom.ui.base.UiState
import id.blossom.utils.AppConstant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel (private val animeRepository: AnimeRepository) : ViewModel() {
    private val _uiStateRecentAnime = MutableStateFlow<UiState<List<RecentAnimeData>>>(UiState.Loading)
    val uiStateRecentAnime: StateFlow<UiState<List<RecentAnimeData>>> = _uiStateRecentAnime

    private val _uiStateOngoingAnime = MutableStateFlow<UiState<List<OngoingAnimeDataItem>>>(UiState.Loading)
    val uiStateOngoingAnime: StateFlow<UiState<List<OngoingAnimeDataItem>>> = _uiStateOngoingAnime

    private val _uiStateGenresAnime = MutableStateFlow<UiState<List<GenresAnimeDataItem>>>(UiState.Loading)
    val uiStateGenresAnime: StateFlow<UiState<List<GenresAnimeDataItem>>> = _uiStateGenresAnime


    init {
        fetchRecentAnime(AppConstant.ALL)
        fetchOngoingAnime(1)
        fetchGenresAnime()
    }

    fun fetchRecentAnime(order_by : String) {
        viewModelScope.launch {
            animeRepository.getRecentAnime(order_by)
                .catch { e ->
                    _uiStateRecentAnime.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiStateRecentAnime.value = UiState.Success(it)
                }
        }
    }

    fun fetchOngoingAnime(page : Int) {
        viewModelScope.launch {
            animeRepository.getOngoingAnime(page)
                .catch { e ->
                    _uiStateOngoingAnime.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiStateOngoingAnime.value = UiState.Success(it)
                }
        }
    }

    fun fetchGenresAnime() {
        viewModelScope.launch {
            animeRepository.getGenresAnime()
                .catch { e ->
                    _uiStateGenresAnime.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiStateGenresAnime.value = UiState.Success(it)
                }
        }
    }

}