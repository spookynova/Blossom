package id.blossom.data.model.anime.stream

import com.google.gson.annotations.SerializedName

data class StreamAnimeResponse(

	@field:SerializedName("episodeActive")
	val episodeActive: String? = null,

	@field:SerializedName("data")
	val data: List<StreamAnimeDataItem>? = null,

	@field:SerializedName("episodeUrl")
	val episodeUrl: List<EpisodeUrlItem>? = null,

	@field:SerializedName("statusbar")
	val statusbar: String? = null,

	@field:SerializedName("isActive")
	val isActive: String? = null
)

data class EpisodeUrlItem(

	@field:SerializedName("size")
	val size: String? = null,

	@field:SerializedName("episode")
	val episode: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class StreamAnimeDataItem(

	@field:SerializedName("episodeText")
	val episodeText: String? = null,

	@field:SerializedName("episodeId")
	val episodeId: String? = null
)
