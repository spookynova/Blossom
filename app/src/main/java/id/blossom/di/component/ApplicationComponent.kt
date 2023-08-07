package id.blossom.di.component

import android.content.Context
import dagger.Component

import id.blossom.BlossomApp
import id.blossom.data.api.NetworkService
import id.blossom.data.repository.anime.AnimeRepository
import id.blossom.data.repository.anime.LocalAnimeRepository
import id.blossom.di.ApplicationContext
import id.blossom.di.module.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: BlossomApp)
    @ApplicationContext
    fun getContext(): Context
    fun getNetworkService(): NetworkService
    fun getAnimeRepository(): AnimeRepository
    fun getLocalAnimeRepository(): LocalAnimeRepository

}