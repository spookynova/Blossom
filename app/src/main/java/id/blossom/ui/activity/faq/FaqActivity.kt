package id.blossom.ui.activity.faq

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.blossom.databinding.ActivityFaqBinding
import id.blossom.utils.Utils

class FaqActivity : AppCompatActivity() {

    lateinit var binding: ActivityFaqBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils().setFullScreenFlag(window)

    }


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, FaqActivity::class.java)
            context.startActivity(intent)
        }
    }

}