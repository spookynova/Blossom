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
import id.blossom.data.storage.entity.FavoriteEntity
import id.blossom.databinding.ActivityDetailBinding
import id.blossom.databinding.ItemGenreHolderBinding
import id.blossom.di.component.DaggerActivityComponent
import id.blossom.di.module.ActivityModule
import id.blossom.ui.activity.detail.adapter.EpisodeAnimeListAdapter
import id.blossom.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailAnimeActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    lateinit var genreBinding: ItemGenreHolderBinding

    @Inject
    lateinit var detailAnimeViewModel: DetailAnimeViewModel
    var animeId: String = ""
    var animeTitle: String = ""
    var animeImage: String = ""

    var favoriteEntity: FavoriteEntity? = null

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
        detailAnimeViewModel.getFavoriteAnimeById(animeId)

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
            detailAnimeViewModel.fetchDetailAnime(animeId)
            detailAnimeViewModel.getFavoriteAnimeById(animeId)
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailAnimeViewModel.uiStateDetailAnime.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressDetailAnimeCover.visibility = View.GONE
                            renderDetailAnime(it.data[0])
                            renderEpisodeAnimeList(it.data[0].episode!!)
                        }
                        is UiState.Loading -> {
                            binding.progressDetailAnimeCover.visibility = View.VISIBLE
                        }
                        is UiState.Error -> {
                            //Handle Error
                            Toast.makeText(this@DetailAnimeActivity, it.message, Toast.LENGTH_LONG)
                                .show()
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
        }

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