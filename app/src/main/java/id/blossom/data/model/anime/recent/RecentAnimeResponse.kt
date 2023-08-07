package id.blossom.data.model.anime.recent

import com.google.gson.annotations.SerializedName

data class RecentAnimeResponse(

	@field:SerializedName("data")
	val data: List<RecentAnimeData>? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class RecentAnimeData(

	@field:SerializedName("animeId")
	val animeId: String? = null,

	@field:SerializedName("typeList")
	val typeList: RecentAnimeTypeList? = null,

	@field:SerializedName("ratings")
	val ratings: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("image")
	val image: String? = null
)

data class RecentAnimeTypeList(

	@field:SerializedName("type2")
	val type2: String? = null,

	@field:SerializedName("type1")
	val type1: String? = null
)
