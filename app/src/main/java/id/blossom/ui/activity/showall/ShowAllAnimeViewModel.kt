package id.blossom.ui.activity.showall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.blossom.data.model.anime.ongoing.OngoingAnimeDataItem
import id.blossom.data.model.anime.recent.RecentAnimeData
import id.blossom.data.model.anime.schedule.ScheduleAnimeDataItem
import id.blossom.data.repository.anime.AnimeRepository
import id.blossom.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ShowAllAnimeViewModel(private val animeRepository: AnimeRepository) : ViewModel() {
    private val _uiStateShowAllRecentAnime =
        MutableLiveData<UiState<List<RecentAnimeData>>>(UiState.Loading)
    val uiStateShowAllRecentAnime: LiveData<UiState<List<RecentAnimeData>>> =
        _uiStateShowAllRecentAnime

    private val _uiStateOngoingAnime = MutableLiveData<UiState<List<OngoingAnimeDataItem>>>(UiState.Loading)
    val uiStateOngoingAnime: LiveData<UiState<List<OngoingAnimeDataItem>>> = _uiStateOngoingAnime

    init {
        fetchRecentAnime("most_viewed")
        fetchOngoingAnime(1)
    }

    fun fetchRecentAnime(order_by: String) {
        viewModelScope.launch {
            animeRepository.getRecentAnime(order_by)
                .catch { e ->
                    _uiStateShowAllRecentAnime.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiStateShowAllRecentAnime.value = UiState.Success(it)
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
}