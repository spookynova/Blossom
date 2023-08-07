package id.blossom.data.model.anime.ongoing

import com.google.gson.annotations.SerializedName

data class OngoingAnimeResponse(

	@field:SerializedName("data")
	val data: List<OngoingAnimeDataItem>? = null,

	@field:SerializedName("order_by")
	val orderBy: String? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class OngoingAnimeDataItem(

	@field:SerializedName("animeId")
	val animeId: String? = null,

	@field:SerializedName("episode")
	val episode: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("image")
	val image: String? = null
)
