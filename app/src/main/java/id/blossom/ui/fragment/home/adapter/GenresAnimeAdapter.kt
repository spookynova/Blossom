package id.blossom.ui.fragment.home.adapter

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.blossom.data.model.anime.genres.GenresAnimeDataItem
import id.blossom.data.model.anime.ongoing.OngoingAnimeDataItem
import id.blossom.databinding.ItemAnimeHolderBinding
import id.blossom.databinding.ItemGenreHolderBinding
import id.blossom.ui.activity.detail.DetailAnimeActivity
import id.blossom.ui.activity.genres.GenresResultActivity

class GenresAnimeAdapter (
    private val genreList: ArrayList<GenresAnimeDataItem>
) : RecyclerView.Adapter<GenresAnimeAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: ItemGenreHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(value: GenresAnimeDataItem, position: Int) {
            binding.genreName.text = value.genreName.toString()
            itemView.setOnClickListener {
                GenresResultActivity.start(it.context, value.genreId.toString(), value.genreName.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            ItemGenreHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        // check if genrelist position is last,then add margin end to the card
        holder.bind(genreList[position],position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    fun addData(list: List<GenresAnimeDataItem>) {
        genreList.addAll(list)
    }


    fun clearData() {
        genreList.clear()
    }

}