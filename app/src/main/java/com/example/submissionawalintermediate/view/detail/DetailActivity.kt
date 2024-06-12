package com.example.submissionawalintermediate.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.submissionawalintermediate.R
import com.example.submissionawalintermediate.databinding.ActivityDetailBinding
import com.example.submissionawalintermediate.response.ListStoryItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail Story"

        setDetail()
    }

    private fun setDetail() {
        val storyItem: ListStoryItem? = intent.getParcelableExtra(EXTRA_DATA)
        storyItem?.let {
            binding.apply {
                tvDetailName.text = it.name
                tvDetailDescription.text = it.description
                Glide.with(this@DetailActivity).load(it.photoUrl).fitCenter().into(ivDetailPhoto)
            }
        }
    }

    companion object {
        const val EXTRA_DATA = "detail"
    }
}