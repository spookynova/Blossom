package id.blossom.ui.fragment.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.blossom.BlossomApp
import id.blossom.databinding.FragmentFavoriteBinding
import id.blossom.di.component.DaggerActivityComponent
import id.blossom.di.module.ActivityModule
import id.blossom.ui.MainActivity
import id.blossom.ui.base.UiState
import id.blossom.ui.fragment.favorite.adapter.FavoriteAnimeAdapter
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    // Declare the ViewBinding variable
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var favoriteViewModel: FavoriteViewModel

    @Inject
    lateinit var favoriteAnimeAdapter: FavoriteAnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDependencies()
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        favoriteViewModel.getFavoriteAnime()
        binding.animeFavoriteRv.adapter = favoriteAnimeAdapter
        binding.animeFavoriteRv.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun setupObserver() {
        favoriteViewModel.listFavoriteLiveData.observe(context as MainActivity) {
            when(it) {
                is UiState.Loading -> {
                    binding.animeFavoriteRv.visibility = View.GONE
                    binding.progressSearchAnime.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    if (it.data.isEmpty()) {
                        binding.animeFavoriteRv.visibility = View.GONE
                        binding.progressSearchAnime.visibility = View.GONE
                        binding.noData.visibility = View.VISIBLE
                        return@observe
                    } else {
                        binding.animeFavoriteRv.visibility = View.VISIBLE
                        binding.progressSearchAnime.visibility = View.GONE
                        binding.noData.visibility = View.GONE
                        favoriteAnimeAdapter.clearData()
                        favoriteAnimeAdapter.addData(it.data)
                        favoriteAnimeAdapter.notifyDataSetChanged()
                    }

                }
                is UiState.Error -> {
                    binding.animeFavoriteRv.visibility = View.VISIBLE
                    binding.progressSearchAnime.visibility = View.GONE
                }
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Don't forget to clear the binding reference to avoid memory leaks
        _binding = null
    }

    companion object {
        // Your companion object if needed
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