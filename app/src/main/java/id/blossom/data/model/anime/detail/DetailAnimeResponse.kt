package id.blossom.data.model.anime.detail

import com.google.gson.annotations.SerializedName

data class DetailAnimeResponse(

	@field:SerializedName("data")
	val data: List<DetailAnimeDataItem>? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class DetailAnimeDataItem(

	@field:SerializedName("explisit")
	val explisit: String? = null,

	@field:SerializedName("studio")
	val studio: String? = null,

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("aired")
	val aired: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("demografis")
	val demografis: String? = null,

	@field:SerializedName("adaptation")
	val adaptation: String? = null,

	@field:SerializedName("episode")
	val episode: List<DetailAnimeEpisodeItem>? = null,

	@field:SerializedName("synopsis")
	val synopsis: String? = null,

	@field:SerializedName("ratingText")
	val ratingText: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("totalEps")
	val totalEps: String? = null,

	@field:SerializedName("peminat")
	val peminat: String? = null,

	@field:SerializedName("ratings")
	val ratings: String? = null,

	@field:SerializedName("genres")
	val genres: List<String?>? = null,

	@field:SerializedName("season")
	val season: String? = null,

	@field:SerializedName("theme")
	val theme: String? = null,

	@field:SerializedName("animeQuality")
	val animeQuality: String? = null,

	@field:SerializedName("credit")
	val credit: String? = null,

	@field:SerializedName("skors")
	val skors: String? = null,

	@field:SerializedName("englishTitle")
	val englishTitle: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DetailAnimeEpisodeItem(

	@field:SerializedName("episodeId")
	val episodeId: String? = null,

	@field:SerializedName("epsTitle")
	val epsTitle: String? = null
)
