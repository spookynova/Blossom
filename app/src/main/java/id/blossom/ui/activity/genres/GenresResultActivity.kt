package id.blossom.ui.activity.genres

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jcodecraeer.xrecyclerview.XRecyclerView
import id.blossom.BlossomApp
import id.blossom.data.model.anime.genres.result.PropertyGenresAnimeDataItem
import id.blossom.databinding.ActivityGenresResultBinding
import id.blossom.di.component.DaggerActivityComponent
import id.blossom.di.module.ActivityModule
import id.blossom.ui.activity.genres.adapter.GenresResultAdapter
import id.blossom.ui.base.UiState
import id.blossom.utils.Utils
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenresResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityGenresResultBinding

    @Inject
    lateinit var genresResultViewModel: GenresResultViewModel

    @Inject
    lateinit var genresResultAdapter: GenresResultAdapter

    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenresResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        injectDependencies()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        val genreId = intent.getStringExtra("genreId")
        val genreName = intent.getStringExtra("genreName")

        binding.tvSelectedGenre.text = "Hasil Pencarian Genre $genreName"
        if (genreId != null) {
            genresResultViewModel.fetchGenresResultAnime(genreId)
        } else {
            Toast.makeText(this, "Genre Id is null", Toast.LENGTH_LONG).show()
        }

        binding.animeResultGenreRv.adapter = genresResultAdapter
        binding.animeResultGenreRv.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.animeResultGenreRv.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                page = 1
                //genresResultViewModel.fetchGenresResultAnime(genreId.toString())
            }

            override fun onLoadMore() {
                page++
                //scheduleViewModel.fetchScheduleAnime(page, queryDays.toString())
            }

        })
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                genresResultViewModel.uiStateGenresResultAnime.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressPropertiesGenre.visibility = View.GONE
                            renderPropertiesGenre(it.data)
                        }
                        is UiState.Loading -> {
                            binding.progressPropertiesGenre.visibility = View.VISIBLE
                        }
                        is UiState.Error -> {
                            //Handle Error
                            Toast.makeText(this@GenresResultActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderPropertiesGenre(data: List<PropertyGenresAnimeDataItem>) {
        genresResultAdapter.clearData()
        genresResultAdapter.addData(data)
        genresResultAdapter.notifyDataSetChanged()
    }


    private fun injectDependencies() {
        DaggerActivityComponent.builder()
            .applicationComponent((application as BlossomApp).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()
            .inject(this)
    }


    companion object {
        fun start(context : Context, genreId : String, genreName : String ){
            val intent = Intent(context, GenresResultActivity::class.java)
            intent.putExtra("genreId", genreId)
            intent.putExtra("genreName", genreName)
            context.startActivity(intent)
        }
    }
}