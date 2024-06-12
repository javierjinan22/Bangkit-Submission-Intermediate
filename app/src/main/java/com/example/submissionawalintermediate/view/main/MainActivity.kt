package com.example.submissionawalintermediate.view.main


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionawalintermediate.R
import com.example.submissionawalintermediate.adapter.LoadingStateAdapter
import com.example.submissionawalintermediate.adapter.StoryAdapter
import com.example.submissionawalintermediate.databinding.ActivityMainBinding
import com.example.submissionawalintermediate.view.ViewModelFactory
import com.example.submissionawalintermediate.view.maps.MapsActivity
import com.example.submissionawalintermediate.view.register.RegisterActivity
import com.example.submissionawalintermediate.view.upload.AddStory


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            } else {
                setupRecyclerView()
                getData()
            }
        }
        setupView()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        addStory()
    }

    private fun getData() {
        val adapter = StoryAdapter()
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.storyResponse.observe(this, {
            adapter.submitData(lifecycle, it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
                return true
            }
            R.id.action_maps -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setupRecyclerView() {
        binding.rvStories.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun addStory(){
        binding.fabAddStory.setOnClickListener {
            val intent = Intent(this, AddStory::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}