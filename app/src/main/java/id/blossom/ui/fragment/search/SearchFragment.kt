package id.blossom.ui.fragment.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jcodecraeer.xrecyclerview.XRecyclerView
import id.blossom.BlossomApp
import id.blossom.data.model.anime.recent.RecentAnimeData
import id.blossom.data.model.anime.search.SearchAnimeDataItem
import id.blossom.databinding.FragmentSearchBinding
import id.blossom.di.component.DaggerActivityComponent
import id.blossom.di.module.ActivityModule
import id.blossom.ui.MainActivity
import id.blossom.ui.base.UiState
import id.blossom.ui.fragment.search.adapter.SearchAnimeAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment() {
    // Declare the ViewBinding variable
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchViewModel: SearchViewModel

    @Inject
    lateinit var searchAnimeAdapter: SearchAnimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDependencies()
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Don't forget to clear the binding reference to avoid memory leaks
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }

    private fun setupUI(){
        // text watcher
        binding.noData.visibility = View.GONE
        binding.searchAnimeEt.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().isNotEmpty()) {
                        searchViewModel.fetchSearchAnime(s.toString())
                    } else {
                        binding.progressSearchAnime.visibility = View.GONE
                        binding.noData.visibility = View.GONE
                        searchAnimeAdapter.clearData()
                        searchAnimeAdapter.notifyDataSetChanged()
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    // do nothing
                }
            }
        )


        // recycler view
        binding.animeSearchRv.adapter = searchAnimeAdapter
        // staggered grid layout manager
        binding.animeSearchRv.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            setLoadingMoreEnabled(false)
            setLoadingListener(object : XRecyclerView.LoadingListener{
                override fun onRefresh() {
                    searchViewModel.fetchSearchAnime(binding.searchAnimeEt.text.toString())
                }

                override fun onLoadMore() {
                    // do nothing
                }

            })
        }

    }

    private fun setupObserver(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.uiStateSearchAnime.collect {
                    when (it) {
                        is UiState.Success -> {
                            binding.progressSearchAnime.visibility = View.GONE
                            if (it.data.isNotEmpty()) {
                                renderListSearch(it.data)
                            } else {
                                binding.noData.visibility = View.VISIBLE
                            }
                        }
                        is UiState.Loading -> {
                            if (binding.searchAnimeEt.text.toString().isNotEmpty()) binding.progressSearchAnime.visibility = View.VISIBLE
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

    private fun renderListSearch (list: List<SearchAnimeDataItem>){
        searchAnimeAdapter.clearData()
        searchAnimeAdapter.addData(list)
        searchAnimeAdapter.notifyDataSetChanged()
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