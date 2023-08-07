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
        fun bind(value: DetailAnimeEpisodeItem, position: Int) {
            binding.tvEpisodeListTitle.text = value.epsTitle.toString()
            itemView.setOnClickListener {
                StreamAnimeActivity.start(it.context, value.episodeId.toString(), "")
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