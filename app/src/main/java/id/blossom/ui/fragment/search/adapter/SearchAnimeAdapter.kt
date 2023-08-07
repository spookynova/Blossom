package id.blossom.ui.fragment.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.blossom.data.model.anime.ongoing.OngoingAnimeDataItem
import id.blossom.data.model.anime.search.SearchAnimeDataItem
import id.blossom.databinding.ItemAnimeHolderBinding
import id.blossom.databinding.ItemAnimeHolderFullBinding
import id.blossom.ui.activity.detail.DetailAnimeActivity
import id.blossom.ui.fragment.home.adapter.OnGoingAnimeAdapter

class SearchAnimeAdapter (
    private val searchList: ArrayList<SearchAnimeDataItem>
) : RecyclerView.Adapter<SearchAnimeAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemAnimeHolderFullBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(value: SearchAnimeDataItem) {
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
        searchList.map {
            if (it.animeId == "" || it.animeId == null) {
                searchList.remove(it)
            }
        }
        return searchList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(searchList[position])

    fun addData(list: List<SearchAnimeDataItem>) {
        searchList.addAll(list)
    }

    fun clearData() {
        searchList.clear()
    }

}