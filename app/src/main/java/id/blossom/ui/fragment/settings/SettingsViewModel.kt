package id.blossom.ui.fragment.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.blossom.data.repository.anime.AnimeRepository
import id.blossom.data.repository.anime.LocalAnimeRepository
import id.blossom.data.storage.entity.FavoriteEntity
import id.blossom.data.storage.entity.UserSettingsEntity
import id.blossom.ui.base.UiState
import kotlinx.coroutines.launch

class SettingsViewModel (
    private val animeRepository: AnimeRepository,
    private val localAnimeRepository: LocalAnimeRepository
) : ViewModel() {
    private val userSettingsLiveData =
        MutableLiveData<UiState<UserSettingsEntity>>(UiState.Loading)
    val userSettings: LiveData<UiState<UserSettingsEntity>> = userSettingsLiveData
    fun getUserSettings() {
        viewModelScope.launch {
            val userSetting = localAnimeRepository.getUserSettings()
            userSettingsLiveData.postValue(UiState.Success(userSetting))
        }
    }

    fun updateUserSettings(userSettingsEntity: UserSettingsEntity) {
        viewModelScope.launch {
            localAnimeRepository.updateUserSettings(userSettingsEntity)
        }
    }
}