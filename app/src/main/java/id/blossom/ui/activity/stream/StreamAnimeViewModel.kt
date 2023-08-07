package id.blossom.ui.activity.stream

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.blossom.data.model.anime.recent.RecentAnimeData
import id.blossom.data.model.anime.stream.EpisodeUrlItem
import id.blossom.data.model.anime.stream.StreamAnimeResponse
import id.blossom.data.repository.anime.AnimeRepository
import id.blossom.data.repository.anime.LocalAnimeRepository
import id.blossom.data.storage.entity.CurrentWatchEntity
import id.blossom.data.storage.entity.UserSettingsEntity
import id.blossom.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class StreamAnimeViewModel (private val animeRepository: AnimeRepository, private val localAnimeRepository: LocalAnimeRepository) : ViewModel() {
    var savedStateDuration = 0L
    var isAlreadyPlaying = false


    private val _uiStateEpisodeUrlAnime =
        MutableLiveData<UiState<StreamAnimeResponse>>(UiState.Loading)
    val uiStateEpisodeUrlAnime: MutableLiveData<UiState<StreamAnimeResponse>> = _uiStateEpisodeUrlAnime


    private val _uiStateCurrentWatch = MutableLiveData<CurrentWatchEntity>()
    val uiStateCurrentWatch: LiveData<CurrentWatchEntity> = _uiStateCurrentWatch


    private val _uiStateUserSetting = MutableLiveData<UserSettingsEntity>()
    val uiStateUserSetting: LiveData<UserSettingsEntity> = _uiStateUserSetting


    init {

    }

    fun fetchEpisodeUrlAnime(animeId : String) {
        viewModelScope.launch {
            animeRepository.getStreamLinkAnime(animeId)
                .catch { e ->
                    _uiStateEpisodeUrlAnime.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiStateEpisodeUrlAnime.value = UiState.Success(it)
                }
        }
    }


    fun setSavedStateDuration(data : CurrentWatchEntity) {
        localAnimeRepository.insertCurrentWatch(data)
    }

    fun updateSavedStateDuration(isAlreadyPlaying : Boolean, duration : Long, currentDuration: Long, episodeId : String) {
        localAnimeRepository.updateCurrentWatchByEpisodeId(isAlreadyPlaying, duration,currentDuration ,episodeId)
    }


    fun getCurrentWatchByAnimeId(animeId: String) {
        viewModelScope.launch {
            val currentState = localAnimeRepository.getCurrentWatchByAnimeId(animeId)
            _uiStateCurrentWatch.postValue(currentState)
        }
    }

    fun getCurrentWatchByEpisodeId(episodeId: String) {
        viewModelScope.launch {
            val currentState = localAnimeRepository.getCurrentWatchByEpisodeId(episodeId)
            _uiStateCurrentWatch.postValue(currentState)
        }

    }


    fun getUserSetting() {
        viewModelScope.launch {
            val userSetting = localAnimeRepository.getUserSettings()
            _uiStateUserSetting.postValue(userSetting)
        }
    }

    fun setUserSetting(userSettingsEntity: UserSettingsEntity) {
        localAnimeRepository.insertUserSettings(userSettingsEntity)
    }

}