package id.blossom.ui.activity.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import id.blossom.BuildConfig
import id.blossom.databinding.ActivityAboutBinding
import id.blossom.utils.Utils

class AboutActivity : AppCompatActivity() {

    lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils().setFullScreenFlag(window)
        val customBrowser = CustomTabsIntent.Builder().build()

        binding.tvVersion.text = "Version ${BuildConfig.VERSION_NAME}"
        binding.llGithub.setOnClickListener {
            customBrowser.launchUrl(this, Uri.parse("https://github.com/evnx32"))
        }

        binding.llApi.setOnClickListener {
            customBrowser.launchUrl(
                this,
                Uri.parse("https://github.com/LuckyIndraEfendi/AnimeIndo-Rest-API")
            )
        }

        binding.llLicense.setOnClickListener {
            customBrowser.launchUrl(
                this,
                Uri.parse("https://www.apache.org/licenses/LICENSE-2.0"))
        }

        binding.llInstagram.setOnClickListener {
            customBrowser.launchUrl(
                this,
                Uri.parse("https://www.instagram.com/dword_ptr/")
            )
        }

        binding.llFacebook.setOnClickListener {
            customBrowser.launchUrl(
                this,
                Uri.parse("https://www.facebook.com/KanMaruKun")
            )
        }

        binding.llEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:evnx32@gmail.com")
            startActivity(intent)
        }

    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }
}