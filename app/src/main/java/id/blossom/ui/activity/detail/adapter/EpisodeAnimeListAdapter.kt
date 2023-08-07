package id.blossom.ui.activity.detail.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.blossom.data.model.anime.detail.DetailAnimeEpisodeItem
import id.blossom.data.model.anime.genres.GenresAnimeDataItem
import id.blossom.databinding.ItemEpisodeHolderBinding
import id.blossom.databinding.ItemGenreHolderBinding
import id.blossom.ui.activity.detail.DetailAnimeActivity
import id.blossom.ui.activity.stream.StreamAnimeActivity
import id.blossom.ui.fragment.home.adapter.GenresAnimeAdapter

class EpisodeAnimeListAdapter(
    private val episodeList: ArrayList<DetailAnimeEpisodeItem>
) : RecyclerView.Adapter<EpisodeAnimeListAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemEpisodeHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val currentWatch = (itemView.context as DetailAnimeActivity).currentWatchList

        fun bind(value: DetailAnimeEpisodeItem, position: Int) {
            binding.tvEpisodeListTitle.text = value.epsTitle.toString()

            currentWatch.map {
                if (it.episodeId == value.episodeId) {
                    val duration = it.duration
                    val currentDuration = it.currentDuration
                    val progress = (currentDuration.toFloat() / duration.toFloat()) * 100
                    binding.progressWatchAnimeEpisode.progress = progress.toInt()
                    Log.e("EpisodeAnimeListAdapter", "bind: $progress")
                } else {
                    Log.e("EpisodeAnimeListAdapter", "bind: else")
                    binding.progressWatchAnimeEpisode.progress = 0
                    binding.progressWatchAnimeEpisode.visibility = View.GONE
                }
            }

            itemView.setOnClickListener {
                StreamAnimeActivity.start(it.context, value.episodeId.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            ItemEpisodeHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return episodeList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        // check if genrelist position is last,then add margin end to the card
        holder.bind(episodeList[position], position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    fun addData(list: List<DetailAnimeEpisodeItem>) {
        episodeList.addAll(list)
    }


    fun clearData() {
        episodeList.clear()
    }
}