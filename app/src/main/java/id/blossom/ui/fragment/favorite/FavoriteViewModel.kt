package id.blossom.ui.fragment.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.blossom.data.repository.anime.AnimeRepository
import id.blossom.data.repository.anime.LocalAnimeRepository
import id.blossom.data.storage.entity.FavoriteEntity
import id.blossom.ui.base.UiState
import kotlinx.coroutines.launch

class FavoriteViewModel (
    private val animeRepository: AnimeRepository,
    private val localAnimeRepository: LocalAnimeRepository
) : ViewModel() {
    private val _listFavoriteLiveData = MutableLiveData<UiState<List<FavoriteEntity>>>(UiState.Loading)
    val listFavoriteLiveData: LiveData<UiState<List<FavoriteEntity>>> = _listFavoriteLiveData

    fun getFavoriteAnime() {
        viewModelScope.launch {
            val favorite = localAnimeRepository.getFavoriteAnime()
            _listFavoriteLiveData.postValue(UiState.Success(favorite))
        }
    }
}