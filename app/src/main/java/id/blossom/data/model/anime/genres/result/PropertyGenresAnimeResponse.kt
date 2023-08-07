package id.blossom.data.model.anime.genres.result

import com.google.gson.annotations.SerializedName

data class PropertyGenresAnimeResponse(

	@field:SerializedName("data")
	val data: List<PropertyGenresAnimeDataItem>? = null,

	@field:SerializedName("genreName")
	val genreName: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class PropertyGenresAnimeDataItem(

	@field:SerializedName("animeId")
	val animeId: String? = null,

	@field:SerializedName("ratings")
	val ratings: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("image")
	val image: String? = null
)
