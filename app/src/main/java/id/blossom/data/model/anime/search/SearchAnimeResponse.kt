package id.blossom.data.model.anime.search

import com.google.gson.annotations.SerializedName

data class SearchAnimeResponse(

	@field:SerializedName("data")
	val data: List<SearchAnimeDataItem>? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class SearchAnimeTypeList(

	@field:SerializedName("type2")
	val type2: String? = null,

	@field:SerializedName("type1")
	val type1: String? = null
)

data class SearchAnimeDataItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("animeId")
	val animeId: String? = null,

	@field:SerializedName("typeList")
	val typeList: SearchAnimeTypeList? = null,

	@field:SerializedName("ratings")
	val ratings: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
