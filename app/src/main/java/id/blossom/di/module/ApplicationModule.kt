package id.blossom.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import id.blossom.BlossomApp
import id.blossom.data.api.NetworkService
import id.blossom.data.storage.AppDatabase
import id.blossom.di.ApplicationContext
import id.blossom.di.BaseUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: BlossomApp) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://anime-indo-rest-api-imkryp70n.vercel.app/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    // Retrofit + OkHttp
    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "blossom").build()
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(database: AppDatabase) = database.favoriteDao()

    @Singleton
    @Provides
    fun provideUserSettingsDao(database: AppDatabase) = database.userSettingsDao()

    @Singleton
    @Provides
    fun provideCurrentWatchDao(database: AppDatabase) = database.currentWatchDao()

    // OkHttp

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Change the level to your desired logging level
        }

        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Add the logging interceptor
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                val request = requestBuilder.build()
                chain.proceed(request)
            }

        return okHttpClientBuilder.build()
    }
}