package id.blossom.ui.fragment.schedule

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.jcodecraeer.xrecyclerview.ArrowRefreshHeader
import com.jcodecraeer.xrecyclerview.XRecyclerView
import id.blossom.BlossomApp
import id.blossom.data.model.anime.schedule.ScheduleAnimeDataItem
import id.blossom.databinding.FragmentScheduleBinding
import id.blossom.di.component.DaggerActivityComponent
import id.blossom.di.module.ActivityModule
import id.blossom.ui.MainActivity
import id.blossom.ui.base.UiState
import id.blossom.ui.fragment.schedule.adapter.ScheduleAnimeAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleFragment : Fragment() {

    // Declare the ViewBinding variable
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private var page = 1

    @Inject
    lateinit var scheduleViewModel: ScheduleViewModel

    @Inject
    lateinit var scheduleAnimeAdapter: ScheduleAnimeAdapter

    private var queryDays: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDependencies()
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.animeScheduleRv.adapter = scheduleAnimeAdapter
        binding.animeScheduleRv.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        }


        // load more data when scroll to bottom
        binding.animeScheduleRv.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                page = 1
                scheduleViewModel.fetchScheduleAnime(1, queryDays.toString())
            }

            override fun onLoadMore() {
                page++
                scheduleViewModel.fetchScheduleAnime(page, queryDays.toString())
            }

        })

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        queryDays = "monday"
                    }
                    1 -> {
                        queryDays = "tuesday"
                    }
                    2 -> {
                        queryDays = "wednesday"
                    }
                    3 -> {
                        queryDays = "thursday"
                    }
                    4 -> {
                        queryDays = "friday"
                    }
                    5 -> {
                        queryDays = "saturday"
                    }
                    6 -> {
                        queryDays = "sunday"
                    }
                }
                binding.progressSheduleAnime.visibility = View.VISIBLE
                binding.animeScheduleRv.visibility = View.GONE
                scheduleAnimeAdapter.clearData()
                page = 1
                scheduleViewModel.fetchScheduleAnime(1, queryDays.toString())

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }

    private fun setupObserver() {

        scheduleViewModel.uiStateScheduleAnime.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    binding.animeScheduleRv.visibility = View.VISIBLE
                    binding.progressSheduleAnime.visibility = View.GONE
                    renderListSchedule(it.data)
                }
                is UiState.Loading -> {
                    binding.progressSheduleAnime.visibility = View.VISIBLE
                }
                is UiState.Error -> {
                    //Handle Error
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    }

    private fun renderListSchedule(data: List<ScheduleAnimeDataItem>) {
        scheduleAnimeAdapter.addData(data)
        scheduleAnimeAdapter.notifyDataSetChanged()
        binding.animeScheduleRv.refreshComplete()
        binding.animeScheduleRv.loadMoreComplete()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Don't forget to clear the binding reference to avoid memory leaks
        binding.animeScheduleRv.destroy()
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