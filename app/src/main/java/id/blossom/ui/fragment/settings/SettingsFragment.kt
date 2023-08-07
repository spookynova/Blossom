package id.blossom.ui.fragment.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import id.blossom.BlossomApp
import id.blossom.R
import id.blossom.data.storage.entity.UserSettingsEntity
import id.blossom.databinding.FragmentSettingsBinding
import id.blossom.di.component.DaggerActivityComponent
import id.blossom.di.module.ActivityModule
import id.blossom.ui.MainActivity
import id.blossom.ui.base.UiState
import javax.inject.Inject

class SettingsFragment : Fragment() {

    // Declare the ViewBinding variable
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val availableVidQuality = arrayOf("360", "480", "720")

    private var userSettings : UserSettingsEntity? = null

    @Inject
    lateinit var settingsViewModel: SettingsViewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        // Setup your UI here

        settingsViewModel.getUserSettings()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, availableVidQuality)
        (binding.tilVideoQuality.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (userSettings != null) {
                userSettings!!.isDarkMode = isChecked
                settingsViewModel.updateUserSettings(userSettings!!)
                setDarkMode(isChecked)
            }
        }

        // get value tilVideoQuality
        binding.tilVideoQuality.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val videoQuality = binding.tilVideoQuality.editText?.text.toString()
                if (userSettings != null) {
                    userSettings!!.vidQuality = videoQuality
                    settingsViewModel.updateUserSettings(userSettings!!)
                }
            }
        }
    }

    private fun setDarkMode(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setupObserver() {
        // Setup your observer here
        settingsViewModel.userSettings.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    // Handle loading state
                }
                is UiState.Success -> {
                    // Handle success state
                    userSettings = it.data

                    if (userSettings != null) {
                        binding.switchDarkMode.isChecked = userSettings!!.isDarkMode
                        (binding.tilVideoQuality.editText as? AutoCompleteTextView)?.setText(userSettings!!.vidQuality)
                        setDarkMode(userSettings!!.isDarkMode)
                    }
                }
                is UiState.Error -> {
                    // Handle error state
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
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