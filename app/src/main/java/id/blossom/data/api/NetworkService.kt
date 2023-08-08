package id.blossom.data.api

import id.blossom.data.model.anime.detail.DetailAnimeResponse
import id.blossom.data.model.anime.genres.GenresAnimeResponse
import id.blossom.data.model.anime.genres.result.PropertyGenresAnimeResponse
import id.blossom.data.model.anime.ongoing.OngoingAnimeResponse
import id.blossom.data.model.anime.recent.RecentAnimeResponse
import id.blossom.data.model.anime.schedule.ScheduleAnimeResponse
import id.blossom.data.model.anime.search.SearchAnimeResponse
import id.blossom.data.model.anime.stream.StreamAnimeResponse
import id.blossom.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    // ---------- lucky anime


    // recent
    @GET("luckyanime/recent")
    suspend fun getRecentAnime(
        @Query("order_by") orderBy: String,
    ) : RecentAnimeResponse

    // ongoing
    @GET("luckyanime/ongoing")
    suspend fun getOngoingAnime(
        @Query("page") page: Int,
    ) : OngoingAnimeResponse

    // detail
    @GET("luckyanime/details{animeId}")
    suspend fun getDetailAnime(
        @Path("animeId", encoded = true) animeId: String,
    ) : DetailAnimeResponse

    // search
    @GET("luckyanime/search")
    suspend fun getSearchAnime(
        @Query("keywq") search: String,
    ) : SearchAnimeResponse

    // schedule
    @GET("luckyanime/schedule")
    suspend fun getScheduleAnime(
        @Query("page") page: Int,
        @Query("scheduled_day") scheduleDay: String,
    ) : ScheduleAnimeResponse

    // genres
    @GET("luckyanime/properties")
    suspend fun getGenresAnime() : GenresAnimeResponse

    // properties genre

    @GET("luckyanime/properties/genre/{genreId}")
    suspend fun getPropertiesGenreAnime(
        @Path("genreId") genreId: String,
        @Query("page") page: Int,
    ) : PropertyGenresAnimeResponse

    // streaming

    @GET("luckyanime/watch/{animeId}")
    suspend fun getStreamingAnime(
        @Path("animeId" , encoded = true) animeId: String,
    ) : StreamAnimeResponse

    // ---------- end lucky anime

}