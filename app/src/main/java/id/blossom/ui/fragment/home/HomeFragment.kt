package id.blossom.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import id.blossom.BlossomApp
import id.blossom.data.model.anime.genres.GenresAnimeDataItem
import id.blossom.data.model.anime.ongoing.OngoingAnimeDataItem
import id.blossom.data.model.anime.recent.RecentAnimeData
import id.blossom.databinding.FragmentHomeBinding
import id.blossom.di.component.DaggerActivityComponent
import id.blossom.di.module.ActivityModule
import id.blossom.ui.MainActivity
import id.blossom.ui.base.UiState
import id.blossom.ui.fragment.home.adapter.GenresAnimeAdapter
import id.blossom.ui.fragment.home.adapter.OnGoingAnimeAdapter
import id.blossom.ui.fragment.home.adapter.RecentAnimeAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var recentAnimeAdapter: RecentAnimeAdapter

    @Inject
    lateinit var ongoingAnimeAdapter: OnGoingAnimeAdapter


    @Inject
    lateinit var genresAnimeAdapter: GenresAnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDependencies()
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Don't forget to clear the binding reference to avoid memory leaks
        _binding = null
    }

    companion object {
        // Your companion object if needed
    }

    private fun setupUI(){



        binding.recentAnimeRv.adapter = recentAnimeAdapter
        binding.ongoingAnimeRv.adapter = ongoingAnimeAdapter
        binding.genreAnimeRv.adapter = genresAnimeAdapter
        // horizontal rv
        binding.recentAnimeRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // horizontal rv
        binding.ongoingAnimeRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // horizontal rv
        binding.genreAnimeRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun setupObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiStateRecentAnime.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressRecentAnime.visibility = View.GONE
                            renderListRecent(it.data)
                        }
                        is UiState.Loading -> {
                            binding.progressRecentAnime.visibility = View.VISIBLE
                        }
                        is UiState.Error -> {
                            //Handle Error
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiStateOngoingAnime.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressOnGoingAnime.visibility = View.GONE
                            renderListOngoing(it.data)
                        }
                        is UiState.Loading -> {
                            binding.progressOnGoingAnime.visibility = View.VISIBLE
                        }
                        is UiState.Error -> {
                            //Handle Error
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiStateGenresAnime.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressGenresAnime.visibility = View.GONE
                            renderListGenres(it.data)
                        }
                        is UiState.Loading -> {
                            binding.progressGenresAnime.visibility = View.VISIBLE
                        }
                        is UiState.Error -> {
                            //Handle Error
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun renderListRecent (list: List<RecentAnimeData>){
        recentAnimeAdapter.clearData()
        recentAnimeAdapter.addData(list)
        recentAnimeAdapter.notifyDataSetChanged()
    }


    private fun renderListOngoing (list: List<OngoingAnimeDataItem>){
        ongoingAnimeAdapter.clearData()
        ongoingAnimeAdapter.addData(list)
        ongoingAnimeAdapter.notifyDataSetChanged()
    }

    private fun renderListGenres (list: List<GenresAnimeDataItem>){
        genresAnimeAdapter.clearData()
        genresAnimeAdapter.addData(list)
        genresAnimeAdapter.notifyDataSetChanged()
    }


    private fun injectDependencies() {
        DaggerActivityComponent
            .builder()
            .applicationComponent((context?.applicationContext as BlossomApp).applicationComponent)
            .activityModule(ActivityModule(activity as MainActivity))
            .build()
            .inject(this)
    }
}