package id.blossom.ui.fragment.schedule.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.blossom.data.model.anime.schedule.ScheduleAnimeDataItem
import id.blossom.data.model.anime.search.SearchAnimeDataItem
import id.blossom.databinding.ItemAnimeHolderFullBinding
import id.blossom.databinding.ItemAnimeScheduleHolderFullBinding
import id.blossom.ui.activity.detail.DetailAnimeActivity
import id.blossom.ui.fragment.search.adapter.SearchAnimeAdapter
import id.blossom.utils.Utils

class ScheduleAnimeAdapter (
    private val scheduleList: ArrayList<ScheduleAnimeDataItem>
) : RecyclerView.Adapter<ScheduleAnimeAdapter.DataViewHolder>() {


    class DataViewHolder(private val binding: ItemAnimeScheduleHolderFullBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val utils = Utils()

        fun bind(value: ScheduleAnimeDataItem) {
            binding.animeTitle.text = value.title.toString()
            binding.animeStatusTxt.text = utils.episodeStringParser("Selanjutnya: ", value.episode.toString())
            binding.animeTimeReleaseTxt.text = value.timeRelease.toString()
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
            ItemAnimeScheduleHolderFullBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int {

        // check if animeId is null, then dont show the item
        scheduleList.map {
            if (it.animeId == "" || it.animeId == null) {
                scheduleList.remove(it)
            }
        }
        return scheduleList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(scheduleList[position])

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    fun addData(list: List<ScheduleAnimeDataItem>) {
        scheduleList.addAll(list)
    }


    fun clearData() {
        scheduleList.clear()
    }
}