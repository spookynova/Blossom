package id.blossom.data.model.anime.schedule

import com.google.gson.annotations.SerializedName

data class ScheduleAnimeResponse(

	@field:SerializedName("scheduled_day")
	val scheduledDay: String? = null,

	@field:SerializedName("data")
	val data: List<ScheduleAnimeDataItem>? = null,

	@field:SerializedName("page")
	val page: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class ScheduleAnimeTypeList(

	@field:SerializedName("type2")
	val type2: String? = null,

	@field:SerializedName("type1")
	val type1: String? = null
)

data class ScheduleAnimeDataItem(

	@field:SerializedName("animeId")
	val animeId: String? = null,

	@field:SerializedName("typeList")
	val typeList: ScheduleAnimeTypeList? = null,

	@field:SerializedName("days")
	val days: String? = null,

	@field:SerializedName("episode")
	val episode: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("timeRelease")
	val timeRelease: String? = null,

	@field:SerializedName("image")
	val image: String? = null
)
