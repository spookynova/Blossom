package id.blossom.ui.activity.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.blossom.data.model.anime.detail.DetailAnimeDataItem
import id.blossom.data.repository.anime.AnimeRepository
import id.blossom.data.repository.anime.LocalAnimeRepository
import id.blossom.data.storage.entity.CurrentWatchEntity
import id.blossom.data.storage.entity.FavoriteEntity
import id.blossom.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailAnimeViewModel(
    private val animeRepository: AnimeRepository,
    private val localAnimeRepository: LocalAnimeRepository
) : ViewModel() {
    private val _uiStateDetailAnime =
        MutableStateFlow<UiState<List<DetailAnimeDataItem>>>(UiState.Loading)
    val uiStateDetailAnime: StateFlow<UiState<List<DetailAnimeDataItem>>> = _uiStateDetailAnime

    private val _favoriteLiveData = MutableLiveData<FavoriteEntity>()
    val favoriteLiveData: LiveData<FavoriteEntity> = _favoriteLiveData

    private val _listFavoriteLiveData = MutableLiveData<List<FavoriteEntity>>()
    val listFavoriteLiveData: LiveData<List<FavoriteEntity>> = _listFavoriteLiveData


    private val _uiStateCurrentWatch = MutableLiveData<UiState<List<CurrentWatchEntity>>>(UiState.Loading)
    val uiStateCurrentWatch: LiveData<UiState<List<CurrentWatchEntity>>> = _uiStateCurrentWatch

    init {

    }

    fun fetchDetailAnime(id: String) {
        viewModelScope.launch {
            animeRepository.getDetailAnime(id)
                .catch { e ->
                    _uiStateDetailAnime.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiStateDetailAnime.value = UiState.Success(it)
                }
        }
    }

    fun getFavoriteAnime() {
        viewModelScope.launch {
            val favorite = localAnimeRepository.getFavoriteAnime()
            _listFavoriteLiveData.postValue(favorite)
        }
    }

    fun setFavoriteAnime(data: FavoriteEntity) {
        localAnimeRepository.insertFavoriteAnime(data)
    }

    fun getFavoriteAnimeById(id: String) {
        // set null _favoriteLiveData
        _favoriteLiveData.postValue(null)
        viewModelScope.launch {
            val favorite = localAnimeRepository.getFavoriteAnimeByAnimeId(id)
            _favoriteLiveData.postValue(favorite)
        }
    }

    fun deleteFavoriteAnimeById(animeId: String) {
        localAnimeRepository.deleteFavoriteAnimeByAnimeId(animeId)
    }


    fun getCurrentWatchByEpisodeId(episodeId: String) {
        viewModelScope.launch {
            Log.e("DetailAnimeViewModel", "getCurrentWatchByEpisodeId: $episodeId")
            val currentState = localAnimeRepository.getAllCurrentWatchByAnimeId(episodeId)
            Log.e("DetailAnimeViewModel", "getCurrentWatchByEpisodeId: $currentState")
            _uiStateCurrentWatch.postValue(UiState.Success(currentState))
        }
    }


}