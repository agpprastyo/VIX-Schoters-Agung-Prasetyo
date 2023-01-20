package com.agprastyo.newsapplication.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.agprastyo.newsapplication.R.id
import com.agprastyo.newsapplication.databinding.ActivityNewsBinding
import com.agprastyo.newsapplication.db.ArticleDatabase
import com.agprastyo.newsapplication.repository.NewsRepository

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    private lateinit var navController: NavController

    private lateinit var binding: ActivityNewsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]


        val navHostFragment = supportFragmentManager.findFragmentById(id.newsNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.apply {
            bottomNavigationView.setupWithNavController(navController)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                Handler(Looper.getMainLooper()).post {
                    when (destination.id) {
                        id.detailFragment -> {
                            binding.bottomNavigationView.visibility = View.GONE
                        }
                        else -> {
                            binding.bottomNavigationView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            Handler(Looper.getMainLooper()).post {
//                when (destination.id) {
//                    id.detailFragment -> {
//                        bottomNavigationView.visibility = View.GONE
//                    }
//
//                    else -> {
//                        bottomNavigationView.visibility = View.VISIBLE
//                    }
//                }
//            }
//        }


    }
}