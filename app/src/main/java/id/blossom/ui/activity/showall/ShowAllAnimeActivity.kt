package id.blossom.ui.activity.showall

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jcodecraeer.xrecyclerview.XRecyclerView
import id.blossom.BlossomApp
import id.blossom.R
import id.blossom.data.model.anime.ongoing.OngoingAnimeDataItem
import id.blossom.data.model.anime.recent.RecentAnimeData
import id.blossom.databinding.ActivityShowAllAnimeBinding
import id.blossom.di.component.DaggerActivityComponent
import id.blossom.di.module.ActivityModule
import id.blossom.ui.activity.showall.adapter.ShowAllOngoingAnimeAdapter
import id.blossom.ui.activity.showall.adapter.ShowAllRecentAnimeAdapter
import id.blossom.ui.base.UiState
import id.blossom.utils.Utils
import javax.inject.Inject

class ShowAllAnimeActivity : AppCompatActivity() {

    lateinit var binding: ActivityShowAllAnimeBinding

    private var route : String? = null
    private var page = 1
    @Inject
    lateinit var showAllAnimeViewModel: ShowAllAnimeViewModel

    @Inject
    lateinit var showAllRecentAnimeAdapter: ShowAllRecentAnimeAdapter

    @Inject
    lateinit var showAllOngoingAnimeAdapter: ShowAllOngoingAnimeAdapter

    private var selectedProperties = ""

    var orderByList : List<String> = listOf("Oldest", "Latest", "Popular", "OnGoing", "Most Viewed", "Updated")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowAllAnimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        injectDependencies()
        Utils().setFullScreenFlag(window)

        setupUI()
        setupObserver()
    }

    private fun setupUI() {

        route = intent.getStringExtra("route")

        if (route == "recent"){
            showAllAnimeViewModel.fetchRecentAnime("")
            binding.allAnimeRv.setLoadingMoreEnabled(false)
            binding.allAnimeRv.adapter = showAllRecentAnimeAdapter

        } else if (route == "ongoing"){
            showAllAnimeViewModel.fetchOngoingAnime(page)
            binding.tvSelectedProperties.text = "Ongoing"
            binding.orderByLayout.visibility = View.GONE
            binding.allAnimeRv.adapter = showAllOngoingAnimeAdapter
        }

        binding.allAnimeRv.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

        val adapter = ArrayAdapter(this, R.layout.list_item, orderByList)
        (binding.orderByLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.orderByDropdown.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedValue = adapter.getItem(position).toString()
            selectedProperties = selectedValue
            when (selectedValue) {
                "Oldest" -> {
                    showAllAnimeViewModel.fetchRecentAnime("oldest")
                }
                "Latest" -> {
                    showAllAnimeViewModel.fetchRecentAnime("latest")
                }
                "Popular" -> {
                    showAllAnimeViewModel.fetchRecentAnime("popular")
                }
                "OnGoing" -> {
                    showAllAnimeViewModel.fetchRecentAnime("ongoing")
                }
                "Most Viewed" -> {
                    showAllAnimeViewModel.fetchRecentAnime("most_viewed")
                }
                "Updated" -> {
                    showAllAnimeViewModel.fetchRecentAnime("updated")
                }

            }
            binding.tvSelectedProperties.text = "Hasil pencarian berdasarkan : $selectedValue"

        }

        binding.allAnimeRv.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onRefresh() {
                if (route == "recent"){
                    showAllAnimeViewModel.fetchRecentAnime(selectedProperties)
                } else if (route == "ongoing"){
                    page = 1
                    showAllAnimeViewModel.fetchOngoingAnime(page)
                }
            }

            override fun onLoadMore() {
                if (route == "ongoing"){
                    page++
                    showAllAnimeViewModel.fetchOngoingAnime(page)
                }
            }
        })
    }

    private fun setupObserver(){
        showAllAnimeViewModel.uiStateShowAllRecentAnime.observe(this){
            when(it){
                is UiState.Success -> {
                    binding.progressPropertiesGenre.visibility = View.GONE

                    if (it.data.isEmpty()){
                        binding.noData.visibility = View.VISIBLE
                        return@observe
                    }
                    renderAllRecentAnime(it.data)
                }
                is UiState.Loading -> {
                    binding.progressPropertiesGenre.visibility = View.VISIBLE
                }
                is UiState.Error -> {
                    //Handle Error
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        showAllAnimeViewModel.uiStateOngoingAnime.observe(this){
            when(it){
                is UiState.Success -> {
                    binding.progressPropertiesGenre.visibility = View.GONE

                    if (it.data.isEmpty()){
                        binding.noData.visibility = View.VISIBLE
                        return@observe
                    }
                    renderAllOngoingAnime(it.data)
                }
                is UiState.Loading -> {
                    binding.progressPropertiesGenre.visibility = View.VISIBLE
                }
                is UiState.Error -> {
                    //Handle Error
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun renderAllRecentAnime(data: List<RecentAnimeData>) {
        showAllRecentAnimeAdapter.clearData()
        showAllRecentAnimeAdapter.addData(data)
        showAllRecentAnimeAdapter.notifyDataSetChanged()
        binding.allAnimeRv.refreshComplete()
    }


    private fun renderAllOngoingAnime(data: List<OngoingAnimeDataItem>) {
        showAllOngoingAnimeAdapter.addData(data)
        showAllOngoingAnimeAdapter.notifyDataSetChanged()
        binding.allAnimeRv.refreshComplete()
        binding.allAnimeRv.loadMoreComplete()
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
        const val TAG = "ShowAllAnimeActivity"
        fun start(context : Context, route : String){
            val intent = Intent(context, ShowAllAnimeActivity::class.java)
            intent.putExtra("route", route)
            context.startActivity(intent)
        }
    }
}