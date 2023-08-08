package id.blossom.ui.activity.showall.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.blossom.data.model.anime.ongoing.OngoingAnimeDataItem
import id.blossom.data.model.anime.recent.RecentAnimeData
import id.blossom.databinding.ItemAnimeHolderFullBinding
import id.blossom.ui.activity.detail.DetailAnimeActivity
import id.blossom.ui.activity.stream.StreamAnimeActivity

class ShowAllOngoingAnimeAdapter (
    private val allAnimeList: ArrayList<OngoingAnimeDataItem>
) : RecyclerView.Adapter<ShowAllOngoingAnimeAdapter.DataViewHolder>() {


    class DataViewHolder(private val binding: ItemAnimeHolderFullBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(value: OngoingAnimeDataItem) {
            binding.animeTitle.text = value.title.toString()
            binding.animeRateDetail.text = value.episode.toString()
            binding.animeRateIcon.visibility = View.GONE
            Glide.with(binding.animeImageView.context).load(value.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.animeImageView)
            itemView.setOnClickListener {
                StreamAnimeActivity.start(it.context, value.episodeId.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DataViewHolder(
        ItemAnimeHolderFullBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int {

        // check if animeId is null, then dont show the item
        allAnimeList.map {
            if (it.episodeId == "" || it.episodeId == null) {
                allAnimeList.remove(it)
            }
        }
        return allAnimeList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(allAnimeList[position])

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    fun addData(list: List<OngoingAnimeDataItem>) {
        allAnimeList.addAll(list)
    }


    fun clearData() {
        allAnimeList.clear()
    }
}