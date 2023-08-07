package id.blossom.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.blossom.data.model.anime.ongoing.OngoingAnimeDataItem
import id.blossom.databinding.ItemAnimeHolderBinding
import id.blossom.ui.activity.detail.DetailAnimeActivity
import id.blossom.ui.activity.stream.StreamAnimeActivity

class OnGoingAnimeAdapter (
    private val ongoingList: ArrayList<OngoingAnimeDataItem>
) : RecyclerView.Adapter<OnGoingAnimeAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemAnimeHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(value: OngoingAnimeDataItem) {
            binding.animeTitle.text = value.title.toString()
            binding.animeRateDetail.text = value.episode.toString()
            binding.animeRateIcon.visibility = GONE
            Glide.with(binding.animeImageView.context)
                .load(value.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.animeImageView)
            itemView.setOnClickListener {
                StreamAnimeActivity.start(it.context, value.episodeId.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            ItemAnimeHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int {

        // check if animeId is null, then dont show the item
        ongoingList.map {
            if (it.episodeId == "" || it.episodeId == null) {
                ongoingList.remove(it)
            }
        }
        return ongoingList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(ongoingList[position])

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    fun addData(list: List<OngoingAnimeDataItem>) {
        ongoingList.addAll(list)
    }


    fun clearData() {
        ongoingList.clear()
    }

}