package id.blossom.ui.fragment.favorite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.blossom.data.model.anime.genres.GenresAnimeDataItem
import id.blossom.data.model.anime.recent.RecentAnimeData
import id.blossom.data.storage.entity.FavoriteEntity
import id.blossom.databinding.ItemAnimeHolderBinding
import id.blossom.databinding.ItemAnimeHolderFullBinding
import id.blossom.databinding.ItemGenreHolderBinding
import id.blossom.ui.activity.detail.DetailAnimeActivity
import id.blossom.ui.activity.genres.GenresResultActivity
import id.blossom.ui.fragment.home.adapter.GenresAnimeAdapter
import id.blossom.ui.fragment.home.adapter.RecentAnimeAdapter

class FavoriteAnimeAdapter (
    private val recentList: ArrayList<FavoriteEntity>
) : RecyclerView.Adapter<FavoriteAnimeAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemAnimeHolderFullBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(value: FavoriteEntity) {
            binding.animeTitle.text = value.title.toString()
            binding.llAnimeRate.visibility = View.GONE
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
            ItemAnimeHolderFullBinding.inflate(
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

    fun addData(list: List<FavoriteEntity>) {
        recentList.addAll(list)
    }

    fun clearData() {
        recentList.clear()
    }
}