package id.blossom.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.blossom.data.model.anime.recent.RecentAnimeData
import id.blossom.databinding.ItemAnimeHolderBinding
import id.blossom.ui.activity.detail.DetailAnimeActivity

class RecentAnimeAdapter(
    private val recentList: ArrayList<RecentAnimeData>
) : RecyclerView.Adapter<RecentAnimeAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemAnimeHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(value: RecentAnimeData) {
            binding.animeTitle.text = value.title.toString()
            binding.animeRateDetail.text = value.ratings.toString()
            Glide.with(binding.animeImageView.context)
                .load(value.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.animeImageView)
            itemView.setOnClickListener {
                DetailAnimeActivity.start(it.context, value.animeId.toString())
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
        recentList.map {
            if (it.animeId == "" || it.animeId == null) {
                recentList.remove(it)
            }
        }
        return recentList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(recentList[position])

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    fun addData(list: List<RecentAnimeData>) {
        recentList.addAll(list)
    }

    fun clearData() {
        recentList.clear()
    }
}