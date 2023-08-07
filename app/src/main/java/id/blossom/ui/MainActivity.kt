package id.blossom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.blossom.databinding.ActivityMainBinding
import id.blossom.ui.fragment.favorite.FavoriteFragment
import id.blossom.ui.fragment.home.HomeFragment
import id.blossom.ui.fragment.schedule.ScheduleFragment
import id.blossom.ui.fragment.search.SearchFragment
import id.blossom.ui.fragment.settings.SettingsFragment
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // inflate the layout
        setContentView(binding.root) // set the content view
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupBottomNav()
    }


    private fun setupBottomNav() {
        binding.viewPager.apply {
            adapter = ViewPagerAdapter(
                supportFragmentManager
            )
        }
        binding.bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                Log.d("bottom_bar", "Selected index: $newIndex, title: ${newTab.title}")
            }

            // An optional method that will be fired whenever an already selected tab has been selected again.
            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                Log.d("bottom_bar", "Reselected index: $index, title: ${tab.title}")
            }
        })

        binding.bottomBar.setupWithViewPager(binding.viewPager)


    }

    override fun onRestart() {
        super.onRestart()
    }
}
class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> ScheduleFragment()
            3 -> FavoriteFragment()
            4 -> SettingsFragment()
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {
        return 4 // hide settings fragment because it's not ready yet
    }
}
