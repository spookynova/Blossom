package id.blossom.data.model.anime.genres

import com.google.gson.annotations.SerializedName

data class GenresAnimeResponse(

	@field:SerializedName("data")
	val data: List<GenresAnimeDataItem>? = null,

	@field:SerializedName("properties")
	val properties: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class GenresAnimeDataItem(

	@field:SerializedName("genreId")
	val genreId: String? = null,

	@field:SerializedName("genreName")
	val genreName: String? = null
)
