package id.blossom.ui.fragment.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.blossom.BlossomApp
import id.blossom.databinding.FragmentSettingsBinding
import id.blossom.di.component.DaggerActivityComponent
import id.blossom.di.module.ActivityModule
import id.blossom.ui.MainActivity

class SettingsFragment : Fragment() {

    // Declare the ViewBinding variable
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDependencies()
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
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