package id.blossom.di.component

import dagger.Component

import id.blossom.di.ActivityScope
import id.blossom.di.module.ActivityModule
import id.blossom.ui.activity.detail.DetailAnimeActivity
import id.blossom.ui.activity.genres.GenresResultActivity
import id.blossom.ui.activity.showall.ShowAllAnimeActivity
import id.blossom.ui.activity.stream.StreamAnimeActivity
import id.blossom.ui.fragment.favorite.FavoriteFragment
import id.blossom.ui.fragment.home.HomeFragment
import id.blossom.ui.fragment.schedule.ScheduleFragment
import id.blossom.ui.fragment.search.SearchFragment
import id.blossom.ui.fragment.settings.SettingsFragment

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: DetailAnimeActivity)
    fun inject(activity: GenresResultActivity)
    fun inject(activity: StreamAnimeActivity)
    fun inject(activity: ShowAllAnimeActivity)

    // Add the inject function for each fragment
    fun inject(fragment: HomeFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: SearchFragment)
    fun inject(fragment: ScheduleFragment)
    fun inject(fragment: SettingsFragment)
}