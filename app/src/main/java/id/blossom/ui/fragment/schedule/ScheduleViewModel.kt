package id.blossom.ui.fragment.schedule

import android.text.format.DateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.blossom.data.model.anime.recent.RecentAnimeData
import id.blossom.data.model.anime.schedule.ScheduleAnimeDataItem
import id.blossom.data.repository.anime.AnimeRepository
import id.blossom.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ScheduleViewModel (private val animeRepository: AnimeRepository) : ViewModel() {
    private val _uiStateScheduleAnime =
        MutableLiveData<UiState<List<ScheduleAnimeDataItem>>>(UiState.Loading)
    val uiStateScheduleAnime: LiveData<UiState<List<ScheduleAnimeDataItem>>> = _uiStateScheduleAnime

    init {
        fetchScheduleAnime(1, DateFormat.format("EEEE", System.currentTimeMillis()).toString())
    }

    fun fetchScheduleAnime(page : Int,day : String) {
        viewModelScope.launch {
            animeRepository.getScheduleAnime(page,day)
                .catch { e ->
                    _uiStateScheduleAnime.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiStateScheduleAnime.value = UiState.Success(it)
                }
        }
    }
}