package id.blossom.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import id.blossom.data.repository.anime.AnimeRepository
import id.blossom.data.repository.anime.LocalAnimeRepository
import id.blossom.di.ActivityContext
import id.blossom.ui.activity.detail.DetailAnimeViewModel
import id.blossom.ui.activity.detail.adapter.EpisodeAnimeListAdapter
import id.blossom.ui.activity.genres.GenresResultViewModel
import id.blossom.ui.activity.genres.adapter.GenresResultAdapter
import id.blossom.ui.activity.showall.ShowAllAnimeViewModel
import id.blossom.ui.activity.showall.adapter.ShowAllOngoingAnimeAdapter
import id.blossom.ui.activity.showall.adapter.ShowAllRecentAnimeAdapter
import id.blossom.ui.activity.stream.StreamAnimeViewModel
import id.blossom.ui.base.ViewModelProviderFactory
import id.blossom.ui.fragment.favorite.FavoriteViewModel
import id.blossom.ui.fragment.favorite.adapter.FavoriteAnimeAdapter
import id.blossom.ui.fragment.home.HomeViewModel
import id.blossom.ui.fragment.home.adapter.GenresAnimeAdapter
import id.blossom.ui.fragment.home.adapter.OnGoingAnimeAdapter
import id.blossom.ui.fragment.home.adapter.RecentAnimeAdapter
import id.blossom.ui.fragment.schedule.ScheduleViewModel
import id.blossom.ui.fragment.schedule.adapter.ScheduleAnimeAdapter
import id.blossom.ui.fragment.search.SearchViewModel
import id.blossom.ui.fragment.search.adapter.SearchAnimeAdapter
import id.blossom.ui.fragment.settings.SettingsViewModel

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }


    @Provides
    fun provideHomeViewModel(animeRepository: AnimeRepository): HomeViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(HomeViewModel::class) {
                HomeViewModel(animeRepository)
            })[HomeViewModel::class.java]
    }

    @Provides
    fun provideSearchViewModel(animeRepository: AnimeRepository): SearchViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(SearchViewModel::class) {
                SearchViewModel(animeRepository)
            })[SearchViewModel::class.java]
    }

    @Provides
    fun provideScheduleViewModel(animeRepository: AnimeRepository): ScheduleViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(ScheduleViewModel::class) {
                ScheduleViewModel(animeRepository)
            })[ScheduleViewModel::class.java]
    }

    @Provides
    fun provideDetailViewModel(animeRepository: AnimeRepository,localAnimeRepository: LocalAnimeRepository): DetailAnimeViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(DetailAnimeViewModel::class) {
                DetailAnimeViewModel(animeRepository,localAnimeRepository)
            })[DetailAnimeViewModel::class.java]
    }

    @Provides
    fun provideGenresResultViewModel(animeRepository: AnimeRepository): GenresResultViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(GenresResultViewModel::class) {
                GenresResultViewModel(animeRepository)
            })[GenresResultViewModel::class.java]
    }

    @Provides
    fun provideStreamAnimeViewModel(animeRepository: AnimeRepository, localAnimeRepository: LocalAnimeRepository): StreamAnimeViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(StreamAnimeViewModel::class) {
                StreamAnimeViewModel(animeRepository,localAnimeRepository)
            })[StreamAnimeViewModel::class.java]
    }

    @Provides
    fun provideFavoriteViewModel(animeRepository: AnimeRepository, localAnimeRepository: LocalAnimeRepository): FavoriteViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(FavoriteViewModel::class) {
                FavoriteViewModel(animeRepository,localAnimeRepository)
            })[FavoriteViewModel::class.java]
    }

    @Provides
    fun provideSettingViewModel(animeRepository: AnimeRepository, localAnimeRepository: LocalAnimeRepository): SettingsViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(SettingsViewModel::class) {
                SettingsViewModel(animeRepository,localAnimeRepository)
            })[SettingsViewModel::class.java]
    }

    @Provides
    fun provideShowAllAnimeViewModel(animeRepository: AnimeRepository): ShowAllAnimeViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(ShowAllAnimeViewModel::class) {
                ShowAllAnimeViewModel(animeRepository)
            })[ShowAllAnimeViewModel::class.java]
    }
    @Provides
    fun provideRecentAnimeAdapter() = RecentAnimeAdapter(ArrayList())

    @Provides
    fun provideOnGoingAnimeAdapter() = OnGoingAnimeAdapter(ArrayList())

    @Provides
    fun provideGenresAnimeAdapter() = GenresAnimeAdapter(ArrayList())

    @Provides
    fun provideSearchAnimeAdapter() = SearchAnimeAdapter(ArrayList())

    @Provides
    fun provideScheduleAnimeAdapter() = ScheduleAnimeAdapter(ArrayList())

    @Provides
    fun provideEpisodeAnimeListAdapter() = EpisodeAnimeListAdapter(ArrayList())

    @Provides
    fun provideGenresResultAdapter() = GenresResultAdapter(ArrayList())

    @Provides
    fun provideFavoriteAnimeAdapter() = FavoriteAnimeAdapter(ArrayList())

    @Provides
    fun provideShowAllRecentAnimeAdapter() = ShowAllRecentAnimeAdapter(ArrayList())

    @Provides
    fun provideShowAllOnGoingAnimeAdapter() = ShowAllOngoingAnimeAdapter(ArrayList())

}