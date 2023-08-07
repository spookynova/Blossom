package id.blossom.ui.activity.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.blossom.BlossomApp
import id.blossom.R
import id.blossom.data.model.anime.detail.DetailAnimeDataItem
import id.blossom.data.model.anime.detail.DetailAnimeEpisodeItem
import id.blossom.data.model.anime.detail.DetailAnimeResponse
import id.blossom.data.storage.entity.CurrentWatchEntity
import id.blossom.data.storage.entity.FavoriteEntity
import id.blossom.databinding.ActivityDetailBinding
import id.blossom.databinding.ItemEpisodeHolderBinding
import id.blossom.databinding.ItemGenreHolderBinding
import id.blossom.di.component.DaggerActivityComponent
import id.blossom.di.module.ActivityModule
import id.blossom.ui.activity.detail.adapter.EpisodeAnimeListAdapter
import id.blossom.ui.activity.stream.StreamAnimeActivity
import id.blossom.ui.base.UiState
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DetailAnimeActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    lateinit var genreBinding: ItemGenreHolderBinding
    lateinit var episodeBinding : ItemEpisodeHolderBinding

    @Inject
    lateinit var detailAnimeViewModel: DetailAnimeViewModel
    var animeId: String = ""
    var animeTitle: String = ""
    var animeImage: String = ""

    var favoriteEntity: FavoriteEntity? = null

    var episodeList : List<DetailAnimeEpisodeItem> = listOf()
    var currentWatchList: List<CurrentWatchEntity> = listOf()

    @Inject
    lateinit var episodeAnimeListAdapter: EpisodeAnimeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater) // inflate the layout
        setContentView(binding.root) // set the content view
        injectDependencies()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupUI()
        setupObserver()
    }

    private fun setupUI() {

        binding.fabFavorite.visibility = View.GONE
        binding.llDetailAnimeInfo.visibility = View.GONE
        binding.tvDetailAnimeSynopsisFull.setOnClickListener {

            if (binding.tvDetailAnimeSynopsis.tag == "full") {
                binding.tvDetailAnimeSynopsis.tag = "short"
                binding.tvDetailAnimeSynopsis.maxLines = 3
                binding.tvDetailAnimeSynopsisFull.text = "Baca Selengkapnya"
            } else {
                binding.tvDetailAnimeSynopsis.tag = "full"
                binding.tvDetailAnimeSynopsis.maxLines = 100
                binding.tvDetailAnimeSynopsisFull.text = "Tampilkan lebih sedikit"
            }
        }



        binding.fabFavorite.setOnClickListener {
            try {
                if (favoriteEntity != null) {
                    if (favoriteEntity!!.isFavorite){
                        favoriteEntity!!.isFavorite = false
                        detailAnimeViewModel.deleteFavoriteAnimeById(favoriteEntity!!.animeId)
                        binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                        Toast.makeText(this, "Anime dihapus dari favorit", Toast.LENGTH_SHORT).show()
                    } else {
                        favoriteEntity!!.isFavorite = true
                        detailAnimeViewModel.setFavoriteAnime(favoriteEntity!!)
                        binding.fabFavorite.setImageResource(R.drawable.ic_favorite_filled)
                        Toast.makeText(this, "Anime ditambahkan ke favorit", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    favoriteEntity = FavoriteEntity(
                        animeId = animeId,
                        title = animeTitle,
                        image = animeImage,
                        isFavorite = true
                    )
                    detailAnimeViewModel.setFavoriteAnime(favoriteEntity!!)
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_filled)
                    Toast.makeText(this, "Anime ditambahkan ke favorit", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                Log.e("ERROR DATABASE", e.toString())
            }
        }


        animeId = intent.getStringExtra("animeId").toString()

        if (animeId == "") {
            Toast.makeText(this, "Anime id is null", Toast.LENGTH_SHORT).show()
            Thread.sleep(2000)
            finish()
        } else {
            detailAnimeViewModel.getCurrentWatchByAnimeId(animeId)
            detailAnimeViewModel.getAllCurrentWatchByEpisodeId(animeId)
            detailAnimeViewModel.fetchDetailAnime(animeId)
            detailAnimeViewModel.getFavoriteAnimeById(animeId)


        }

        binding.btnDetailAnimeWatch.setOnClickListener {
            if (binding.btnDetailAnimeWatch.tag == "watch"){
                StreamAnimeActivity.start(this, animeId + "/episode/1")
            } else {
                StreamAnimeActivity.start(this, binding.btnDetailAnimeWatch.tag.toString())
            }
        }
    }

    private fun setupObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailAnimeViewModel.uiStateDetailAnime.collect { detailUiState ->
                    when (detailUiState) {
                        is UiState.Success -> {
                            binding.progressDetailAnimeCover.visibility = View.GONE
                            val detailAnime = detailUiState.data.firstOrNull()
                            detailAnime?.let {
                                renderDetailAnime(it)
                                episodeList = it.episode ?: emptyList()
                                //updateEpisodeListAndCurrentWatch()
                            }
                        }
                        is UiState.Loading -> {
                            binding.progressDetailAnimeCover.visibility = View.VISIBLE
                        }
                        is UiState.Error -> {
                            // Handle Error
                            Toast.makeText(this@DetailAnimeActivity, detailUiState.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }


        detailAnimeViewModel.favoriteLiveData.observe(this) { data ->
            if (data != null) {
                if (data.isFavorite) {
                    favoriteEntity = data
                    Log.e("FAVORITE", data.toString())
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite_filled)
                } else {
                    binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
                }
            }
        }

        detailAnimeViewModel.uiStateCurrentWatch.observe(this) { data ->
            if (data != null) {
                renderCurrentWatchProgress(data)
            } else {
                binding.llCurrentEpisodeDetailAnime.visibility = View.GONE
            }
        }

        detailAnimeViewModel.uiStateListCurrentWatch.observe(this) { currentWatchData ->
            currentWatchData?.let { data ->
                currentWatchList = data
 //               updateEpisodeListAndCurrentWatch()
            } ?: run {
//                renderEpisodeAnimeList(episodeList)
                binding.llCurrentEpisodeDetailAnime.visibility = View.GONE
            }
        }

    }

    private fun updateEpisodeListAndCurrentWatch() {
        if (currentWatchList.isNotEmpty()) {
            renderEpisodeAnimeList(episodeList)
        } else {
            renderEpisodeAnimeList(episodeList)
        }

        Log.e("LIST CURRENT WATCH", currentWatchList.toString())
        Log.e("LIST EPISODE", episodeList.toString())
    }

    private fun renderDetailAnime(data: DetailAnimeDataItem) {

        animeTitle = data.title.toString()
        animeImage = data.image.toString()

        binding.fabFavorite.visibility = View.VISIBLE
        binding.llDetailAnimeInfo.visibility = View.VISIBLE

        binding.tvDetailAnimeSeason.text = data.aired + " - " + data.season
        binding.tvDetailAnimeTitle.text = data.title
        binding.tvDetailAnimeSynopsis.text = data.synopsis
        binding.tvDetailAnimeScore.text = data.skors
        binding.tvDetailAnimeStudio.text = data.studio
        binding.tvDetailAnimeRate.text = data.ratingText

        Glide.with(this)
            .load(data.image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imgDetailCoverAnime)

        binding.llDetailAnimeGenre.removeAllViews()
        data.genres?.map {
            genreBinding = ItemGenreHolderBinding.inflate(layoutInflater)
            genreBinding.genreName.text = it
            // color primary
            genreBinding.genreName.setTextColor(resources.getColor(R.color.colorGray500))
            genreBinding.genreCard.setCardBackgroundColor(resources.getColor(R.color.colorGray200))
            binding.llDetailAnimeGenre.addView(genreBinding.root)
            // on click
            genreBinding.genreCard.setOnClickListener { _ ->
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        binding.llDetailAnimeEpisode.removeAllViews()
        data.episode?.map {
            episodeBinding = ItemEpisodeHolderBinding.inflate(layoutInflater)
            episodeBinding.tvEpisodeListTitle.text = it.epsTitle
            if (currentWatchList.isNotEmpty()) {
                val currentWatch = currentWatchList.firstOrNull { currentWatch ->
                    currentWatch.episodeId == it.episodeId
                }
                if (currentWatch != null) {
                    val duration = currentWatch.duration
                    val currentDuration = currentWatch.currentDuration
                    val progress = (currentDuration.toFloat() / duration.toFloat()) * 100
                    episodeBinding.progressWatchAnimeEpisode.progress = progress.toInt()

                } else {
                    episodeBinding.progressWatchAnimeEpisode.progress = 0
                    episodeBinding.progressWatchAnimeEpisode.visibility = View.GONE
                }
            } else {
                episodeBinding.progressWatchAnimeEpisode.progress = 0
                episodeBinding.progressWatchAnimeEpisode.visibility = View.GONE
            }

            binding.llDetailAnimeEpisode.addView(episodeBinding.root)
            episodeBinding.root.setOnClickListener { _ ->
                StreamAnimeActivity.start(this, it.episodeId.toString())
            }
        }

    }

    private fun renderCurrentWatchProgress(data : CurrentWatchEntity) {
        val duration = data.duration
        val currentDuration = data.currentDuration
        val progress = (currentDuration.toFloat() / duration.toFloat()) * 100

        Log.e("CURRENT WATCH", progress.toString())

        binding.tvTotalMinuteDetailAnime.text = TimeUnit.MILLISECONDS.toMinutes(duration).toString() + " Menit"

        binding.tvCurrentEpisodeDetailAnime.text = "Episode "+data.episodeId.substringAfterLast("/episode/").toIntOrNull().toString()
        binding.progressBarDetailAnime.progress = progress.toInt()

        binding.btnDetailAnimeWatch.tag = data.episodeId
        binding.btnDetailAnimeWatch.text = "Lanjutkan menonton" + " Episode "+data.episodeId.substringAfterLast("/episode/").toIntOrNull().toString()
    }

    private fun renderEpisodeAnimeList(data: List<DetailAnimeEpisodeItem>) {
        episodeAnimeListAdapter.clearData()
        episodeAnimeListAdapter.addData(data)
        episodeAnimeListAdapter.notifyDataSetChanged()
        binding.rvDetailAnimeEpisode.adapter = episodeAnimeListAdapter
        binding.rvDetailAnimeEpisode.layoutManager = LinearLayoutManager(this)
    }


    private fun injectDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as BlossomApp).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }


    companion object {
        const val TAG = "DetailAnimeActivity"

        fun start(context: Context, animeId: String) {
            val intent = android.content.Intent(context, DetailAnimeActivity::class.java)
            intent.putExtra("animeId", animeId)
            context.startActivity(intent)
        }
    }
}