package id.blossom.data.repository.anime

import android.util.Log
import id.blossom.data.api.NetworkService
import id.blossom.data.model.anime.detail.DetailAnimeDataItem
import id.blossom.data.model.anime.genres.GenresAnimeDataItem
import id.blossom.data.model.anime.genres.result.PropertyGenresAnimeDataItem
import id.blossom.data.model.anime.ongoing.OngoingAnimeDataItem
import id.blossom.data.model.anime.recent.RecentAnimeData
import id.blossom.data.model.anime.schedule.ScheduleAnimeDataItem
import id.blossom.data.model.anime.search.SearchAnimeDataItem
import id.blossom.data.model.anime.stream.StreamAnimeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnimeRepository @Inject constructor(private val networkService: NetworkService) {
    fun getRecentAnime(order_by: String): Flow<List<RecentAnimeData>> {
        return flow {
            emit(networkService.getRecentAnime(order_by))
        }.map {
            it.data!!
        }
    }

    fun getOngoingAnime(page: Int): Flow<List<OngoingAnimeDataItem>> {
        return flow {
            emit(networkService.getOngoingAnime(page))
        }.map {
            it.data!!
        }
    }

    fun getDetailAnime(animeId: String): Flow<List<DetailAnimeDataItem>> {
        Log.e("AnimeRepository", "getDetailAnime: $animeId")
        return flow {
            emit(networkService.getDetailAnime(animeId))
        }.map {
            it.data!!
        }
    }

    fun getSearchAnime(search: String): Flow<List<SearchAnimeDataItem>> {
        return flow {
            emit(networkService.getSearchAnime(search))
        }.map {
            it.data!!
        }
    }

    fun getScheduleAnime(page: Int, scheduleDay: String): Flow<List<ScheduleAnimeDataItem>> {
        return flow {
            emit(networkService.getScheduleAnime(page, scheduleDay))
        }.map {
            it.data!!
        }
    }

    fun getGenresAnime(): Flow<List<GenresAnimeDataItem>> {
        return flow {
            emit(networkService.getGenresAnime())
        }.map {
            it.data!!
        }
    }

    fun getResultGenresAnime(genres: String, page: Int): Flow<List<PropertyGenresAnimeDataItem>> {
        return flow {
            emit(networkService.getPropertiesGenreAnime(genres, page))
        }.map {
            it.data!!
        }
    }

    fun getStreamLinkAnime(animeId: String): Flow<StreamAnimeResponse> {
        return flow {
            emit(networkService.getStreamingAnime(animeId))
        }.map {
            it
        }
    }

}