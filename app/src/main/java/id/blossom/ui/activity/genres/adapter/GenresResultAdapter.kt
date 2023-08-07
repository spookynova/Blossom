package id.blossom.ui.activity.genres.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.blossom.data.model.anime.genres.result.PropertyGenresAnimeDataItem
import id.blossom.databinding.ItemAnimeHolderFullBinding
import id.blossom.ui.activity.detail.DetailAnimeActivity

class GenresResultAdapter (
    private val propertiesGenreList: ArrayList<PropertyGenresAnimeDataItem>
) : RecyclerView.Adapter<GenresResultAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemAnimeHolderFullBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(value: PropertyGenresAnimeDataItem) {
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
            ItemAnimeHolderFullBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int {

        // check if animeId is null, then dont show the item
        propertiesGenreList.map {
            if (it.animeId == "" || it.animeId == null) {
                propertiesGenreList.remove(it)
            }
        }
        return propertiesGenreList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(propertiesGenreList[position])

    fun addData(list: List<PropertyGenresAnimeDataItem>) {
        propertiesGenreList.addAll(list)
    }

    fun clearData() {
        propertiesGenreList.clear()
    }
}